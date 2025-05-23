package u08.examples

import scala.math.BigDecimal.double2bigDecimal
import scala.u08.utils.Time
import u07.examples.StochasticReadersWriters.spn
import u07.modelling.SPN.toCTMC
import u07.examples.StochasticReadersWriters.Place.*
import u07.utils.MSet

object StochasticChannelExperiment extends App with de.sciss.chart.module.Charting:
  import u08.modelling.CTMCExperiment.*

  val data =
    val ctmc = toCTMC(spn)
    for
      t <- 2 to 7 by 0.1
      p = ctmc.experiment(
        runs = 10000,
        prop = ctmc.globally(places => !(places.asMap.get(READ).getOrElse(0) > 0 && places.asMap.get(WRITE).getOrElse(0) > 0)),
        s0 = MSet(IDLE, IDLE, TOKEN),
      timeBound = t.toDouble)
    yield (t, p)

  Time.timed:
    println:
      data.mkString("\n")

  given ChartTheme = ChartTheme.Default
  val chart = de.sciss.chart.api.XYLineChart(data)
  chart.show("Probability")