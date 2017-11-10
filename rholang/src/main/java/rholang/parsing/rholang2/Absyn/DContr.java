package rholang.parsing.rholang2.Absyn; // Java Package generated by the BNF Converter.

public class DContr extends Contr {
  public final String name_;
  public final ListCPattern listcpattern_;
  public final Proc proc_;
  public DContr(String p1, ListCPattern p2, Proc p3) { name_ = p1; listcpattern_ = p2; proc_ = p3; }

  public <R,A> R accept(rholang.parsing.rholang2.Absyn.Contr.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof rholang.parsing.rholang2.Absyn.DContr) {
      rholang.parsing.rholang2.Absyn.DContr x = (rholang.parsing.rholang2.Absyn.DContr)o;
      return this.name_.equals(x.name_) && this.listcpattern_.equals(x.listcpattern_) && this.proc_.equals(x.proc_);
    }
    return false;
  }

  public int hashCode() {
    return 37*(37*(this.name_.hashCode())+this.listcpattern_.hashCode())+this.proc_.hashCode();
  }


}