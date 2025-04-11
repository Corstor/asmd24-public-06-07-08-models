package u06.examples

import u06.utils.MSet

object PNReadersWriters:

  enum Place:
    case IDLE, ASK, T_READ, T_WRITE, T_ACCESS, READ, WRITE
    
  export Place.*
  export u06.modelling.PetriNet.*
  export u06.modelling.SystemAnalysis.*
  export u06.utils.MSet

  // DSL-like specification of a Petri Net
  def pnRW = PetriNet[Place](
    MSet(IDLE) --> MSet(ASK),
    MSet(ASK) --> MSet(T_READ),
    MSet(ASK) --> MSet(T_WRITE),
    MSet(T_READ, T_ACCESS) --> MSet(READ, T_ACCESS),
    MSet(T_WRITE, T_ACCESS) --> MSet(WRITE) --- MSet(READ),
    MSet(READ) --> MSet(IDLE),
    MSet(WRITE) --> MSet(IDLE, T_ACCESS)
  ).toSystem

@main def mainPNReadersWriters =
  import PNReadersWriters.*
  // example usage
  println(pnRW.paths(MSet(IDLE, IDLE, T_ACCESS),7).toList.mkString("\n"))
