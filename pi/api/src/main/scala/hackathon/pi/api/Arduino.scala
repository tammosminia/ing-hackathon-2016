package hackathon.pi.api



object Arduino {
  val serialTest = new SerialTest
  serialTest.initialize()

  def toArduino(s: String) = {
    serialTest.output.write(s.getBytes)
    serialTest.output.flush()
  }

}
