package net.richarddawkins.wm.morphs.arthro;

/**
 * Warning: this class is not threadsafe.
 * 
 * @author alan
 *
 */
public class AtomPrinter {

  static int segmentCounter;

  protected static void printAt(Atom atom) {
    String kind = "";
    switch (atom.kind) {
    case AnimalTrunk:
      kind = "AnimalTrunk";
    case AnimalJoint:
      kind = "    AnimalJoint";
    case AnimalClaw:
      kind = "    AnimalClaw";
    case SectionTrunk:
      kind = "        SectionTrunk";
    case SectionJoint:
      kind = "            SectionJoint";
    case SectionClaw:
      kind = "            SectionClaw";
    case SegmentTrunk:
      kind = "                SegmentTrunk " + ++segmentCounter;
    case SegmentJoint:
      kind = "                    SegmentJoint";
    case SegmentClaw:
      kind = "                    SegmentClaw";
    case Joint:
      kind = "                        Joint";
    case Claw:
      kind = "                        Claw";
    default:
      break;
    }
    System.out.format("%10.2f %10.2f %10.2f     %s", atom.height, atom.width, atom.angle, kind);
  }

  /**
   * Print this animal Recursively step through the animal
   */
  protected static void print(Atom atom) {
    if (atom.kind != AtomKind.Free)
      printAt(atom);
    if (atom.firstBelowMe != null)
      print(atom.firstBelowMe);
    if (atom.nextLikeMe != null && atom.kind != AtomKind.AnimalTrunk)
      print(atom.nextLikeMe);
  }

  public static void printMiddle(Atom atom) {
    System.out.format("%10s %10s %10s", "Height", "Width", "Angle");
    segmentCounter = 0;
    if (atom.kind == AtomKind.AnimalTrunk)
      print(atom);
  }
}
