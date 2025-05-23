package u07.examples

import java.util.Random

import u07.modelling.{CTMCSimulation, DAP, DAPGrid}
import u07.modelling.CTMCSimulation.*
import u07.utils.{Grids, MSet}

object MESSAGE:
  enum Place:
    case SENDER, RECEIVER, MESSAGE, REPLY, OK
  type ID = (Int, Int)
  val resX = 2
  val resY = 2

  export Place.*
  export u07.modelling.DAP.*
  export u07.modelling.DAPGrid.*
  export u07.modelling.CTMCSimulation.*

  val gossipRules = DAP[Place](
    Rule(MSet(SENDER), m => 1, MSet(SENDER), MSet(MESSAGE)),
    Rule(MSet(MESSAGE), m => 1, MSet(), MSet(MESSAGE)),
    Rule(MSet(MESSAGE, MESSAGE), m => 100000, MSet(MESSAGE), MSet()),
    Rule(MSet(RECEIVER, MESSAGE), m => 1000, MSet(RECEIVER), MSet(REPLY)),
    Rule(MSet(REPLY), m => 1, MSet(), MSet(REPLY)),
    Rule(MSet(REPLY, REPLY), m => 100000, MSet(REPLY), MSet()),
    Rule(MSet(REPLY, MESSAGE), m => 2, MSet(), MSet(REPLY)),
    Rule(MSet(REPLY, SENDER), m => 2, MSet(OK), MSet())
  )
  val gossipCTMC = DAP.toCTMC[ID, Place](gossipRules)
  val net = Grids.createRectangularGrid(5, 5)
  // an `a` initial on top LEFT
  val state = State[ID, Place](
    MSet(Token((0, 0), SENDER), Token((2, 2), RECEIVER)),
    MSet(),
    net
  )

@main def mainDAPGossip =
  import MESSAGE.*
  val offset = 1
  val numberOfPaths = 2
  gossipCTMC
    .newSimulationTrace(state, new Random)
    .takeWhile(e =>
      e.time < resX * resY * numberOfPaths + offset
        &&
        !e.state.tokens.asList
          .find(t => t._2 == Place.OK)
          .isDefined
    )
    .toList
    .foreach: step =>
      println(step._1) // print time
      println("MESSAGE")
      println(DAPGrid.simpleGridStateToString[Place](step._2, Place.MESSAGE))
      println("REPLY")
      println(DAPGrid.simpleGridStateToString[Place](step._2, Place.REPLY))
      println(step._2.tokens.asList.find(token => token._2 == Place.OK))
