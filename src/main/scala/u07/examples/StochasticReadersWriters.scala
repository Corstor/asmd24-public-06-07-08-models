package u07.examples

import u07.modelling.{CTMC, SPN}
import u07.utils.MSet
import java.util.Random

object StochasticReadersWriters extends App:
  // Specification of my data-type for states
  enum Place:
    case IDLE, DECIDE, TRY_READ, TRY_WRITE, TOKEN, READ, WRITE

  export Place.*
  export u07.modelling.CTMCSimulation.*
  export u07.modelling.SPN.*

  val spn = SPN[Place](
    Trn(MSet(IDLE), m => 1.0, MSet(DECIDE), MSet()),
    Trn(MSet(DECIDE), m => 2.0, MSet(TRY_READ), MSet()),
    Trn(MSet(DECIDE), m => 1.0, MSet(TRY_WRITE), MSet()),
    Trn(MSet(TRY_READ, TOKEN), m => 100000.0, MSet(READ, TOKEN), MSet()),
    Trn(MSet(TRY_WRITE, TOKEN), m => 200000.0, MSet(WRITE), MSet(READ)),
    Trn(MSet(READ), m => m(READ), MSet(IDLE), MSet()),
    Trn(MSet(WRITE), m => 2, MSet(IDLE, TOKEN), MSet())
  )

  println:
    toCTMC(spn)
      .newSimulationTrace(MSet(IDLE, IDLE, TOKEN), new Random)
      .take(20)
      .toList
      .mkString("\n")
