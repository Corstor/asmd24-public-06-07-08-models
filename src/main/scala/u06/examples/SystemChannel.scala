package u06.examples

import u06.modelling.System

object SystemChannel:

  // Specification of a data-type for channel states
  enum State:
    case IDLE, SEND, DONE, FAIL

  // enabling analysis through this object
  export u06.modelling.SystemAnalysis.*
  export State.*

  // System specification
  def channel: System[State] = System.ofTransitions(
    IDLE->SEND,
    SEND->SEND, SEND->DONE, SEND->FAIL,
    FAIL->IDLE //,DONE->DONE
  )

@main def mainSystemChannel() =
  import SystemChannel.*
  // Analysis, by querying
  println(channel.normalForm(IDLE))
  println(channel.normalForm(DONE))
  println(channel.next(IDLE))
  println(channel.next(SEND))
  println("IDLE  "+channel.paths(IDLE,1).toList)
  println("ASK  "+channel.paths(IDLE,2).toList)
  println("T_READ  "+channel.paths(IDLE,3).toList)
  println("T_WRITE  "+channel.paths(IDLE,4).toList)
  println("CMP:\n"+channel.completePathsUpToDepth(IDLE,10).mkString("\n"))