package scala.u09.tasks.qlearningdesign

import u09.model.QMatrix
import QMatrix.*
import Move.*

object Corridor extends App :
  val rl: QMatrix.Facade = Facade(
    width = 10,
    height = 10,
    initial = (0,0),
    terminal = { case _ => false },
    reward = { case ((9, 9), _) => 100; case ((x, 3), _) if x <= 7 => -1000; case ((7, y), _) if y >= 3 => -100; case ((5, 0), _) => -100; case ((4, 2), _) => -100; case ((8, 1), _) => -100; case ((9, 5), _) => -100; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
    jumps = Map.empty,
    gamma = 0.9,
    alpha = 0.7,
    epsilon = 0.6,
    epsilonReducer = 0.00002,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(30000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%1s"))

object Collector extends App:
  val rl: QMatrix.Facade = Facade(
    width = 10,
    height = 10,
    initial = (0,0),
    terminal = { case _ => false },
    reward = { case ((3, 4), _) => 10; case ((1, 7), _) => 10; case ((9, 9), _) => 10; case ((6, 3), _) => 10; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
    jumps = { case ((3, 4), _) => (0, 0); case ((1, 7), _) => (0, 0); case ((9, 9), _) => (0, 0); case ((6, 3), _) => (0, 0); },
    gamma = 0.9,
    alpha = 0.7,
    epsilon = 0.8,
    epsilonReducer = 0.00001,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(30000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%1s"))

object HideAndSeek extends App:
  val rl: QMatrix.Facade = Facade(
    width = 10,
    height = 10,
    initial = (0,0),
    terminal = { case (5, y) if y >= 5 => true ; case (x, 3) if x <= 3 => true; case _ => false },
    reward = { case((x, 0), RIGHT) if x >= 5 => -x ; case ((9, 9), _) => 100; case ((0, _), LEFT) => -1; case ((_, 0), UP) => -1; case ((9, _), RIGHT) => -1; case ((_, 9), DOWN) => -1; case _ => 0 },
    jumps = Map.empty,
    gamma = 0.9,
    alpha = 0.7,
    epsilon = 0.8,
    epsilonReducer = 0.00001,
    v0 = 1
  )

  val q0 = rl.qFunction
  println(rl.show(q0.vFunction,"%2.2f"))
  val q1 = rl.makeLearningInstance().learn(40000,400,q0)
  println(rl.show(q1.vFunction,"%2.2f"))
  println(rl.show(s => q1.bestPolicy(s).toString,"%1s"))
