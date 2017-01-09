package net.richarddawkins.watchmaker.morph.arthro;

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
      break;
    case AnimalJoint:
      kind = "    AnimalJoint";
      break;
    case AnimalClaw:
      kind = "    AnimalClaw";
      break;
    case SectionTrunk:
      kind = "        SectionTrunk";
      break;
    case SectionJoint:
      kind = "            SectionJoint";
      break;
    case SectionClaw:
      kind = "            SectionClaw";
      break;
    case SegmentTrunk:
      kind = "                SegmentTrunk " + ++segmentCounter;
      break;
    case SegmentJoint:
      kind = "                    SegmentJoint";
      break;
    case SegmentClaw:
      kind = "                    SegmentClaw";
      break;
    case Joint:
      kind = "                        Joint";
      break;
    case Claw:
      kind = "                        Claw";
      break;
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
