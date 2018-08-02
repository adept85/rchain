package coop.rchain.roscala.ob.mbox

import coop.rchain.roscala.Vm.State
import coop.rchain.roscala.ob.{Ctxt, Invalid, Nil, Niv, Ob}

class QueueMbox(var enabledSet: Ob, val queue: MboxQueue) extends Ob {
  var lockVal: Boolean = true

  override def receiveMsg(client: MboxOb, task: Ctxt, state: State): Ob = {
    MboxOb.logger.debug("Queue mailbox receives message")

    if (isLocked || !enabledSet.accepts(task))
      queue.enqueue(task)
    else {
      lock()
      client.schedule(task, state)
    }

    Niv
  }

  /**
    * `nextMsg` is called after an `Actor` finished updating its
    * state. It gets called when an `Actor` is ready to process more
    * messages.
    *
    * In the case of a `QueueMbox` the mailbox gets the next message
    * from the `MboxQueue` which then gets scheduled.
    */
  override def nextMsg(client: MboxOb, newEnabledSet: Ob, state: State): Ob = {
    MboxOb.logger.debug(s"Next message received on $this")

    if (!isLocked) {
      MboxOb.logger.warn("Next message received on unlocked mailbox")
      return Niv
    }

    val task = queue.maybeDequeue(newEnabledSet)

    if (task == Invalid) {

      /**
        * Either there is no acceptable msg, or the queue was empty to
        * begin with.  In both cases we want to leave the mbox unlocked,
        * either by reverting to the (unique) emptyMbox if the
        * new_enabledSet is NIL, or by resetting lockVal if
        * new_enabledSet is non-NIL.
        */
      if (queue.isEmpty && newEnabledSet == Nil)
        client.mbox = MboxOb.EmptyMbox
      else {
        this.enabledSet = newEnabledSet
        unlock()
      }

    } else {

      /**
        * The mbox is presumably locked at this point, and it should
        * remain so, either by reverting to the (unique) lockedMbox or
        * by keeping lockVal set.
        */
      if (queue.isEmpty && newEnabledSet == Nil)
        client.mbox = MboxOb.LockedMbox
      else
        this.enabledSet = newEnabledSet

      client.schedule(task.asInstanceOf[Ctxt], state)
    }

    Niv
  }

  def isLocked: Boolean = lockVal

  def lock(): Unit = lockVal = true

  def unlock(): Unit = lockVal = false

  def enqueue(value: Ob): Unit = {
    MboxOb.logger.debug(s"Enqueue $value")
    queue.enqueue(value)
  }
}

object QueueMbox {
  def apply(enabledSet: Ob): QueueMbox = {
    val queue = MboxQueue()
    new QueueMbox(enabledSet, queue)
  }
}
