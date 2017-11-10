package rholang.parsing.rholang2.Absyn; // Java Package generated by the BNF Converter.

public class PPtVal extends PPattern {
  public final ValPattern valpattern_;
  public PPtVal(ValPattern p1) { valpattern_ = p1; }

  public <R,A> R accept(rholang.parsing.rholang2.Absyn.PPattern.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof rholang.parsing.rholang2.Absyn.PPtVal) {
      rholang.parsing.rholang2.Absyn.PPtVal x = (rholang.parsing.rholang2.Absyn.PPtVal)o;
      return this.valpattern_.equals(x.valpattern_);
    }
    return false;
  }

  public int hashCode() {
    return this.valpattern_.hashCode();
  }


}