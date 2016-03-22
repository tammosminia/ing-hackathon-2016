package hackathon.pi.api

import java.io.{InputStreamReader, BufferedReader}
import gnu.io.{SerialPort, CommPortIdentifier, SerialPortEvent, SerialPortEventListener}

class Arduino(onReceive: String => Unit) {
  private object EventListener extends SerialPortEventListener {
    def serialEvent(oEvent: SerialPortEvent) {
      if (oEvent.getEventType == SerialPortEvent.DATA_AVAILABLE) {
        try {
          val inputLine: String = input.readLine
          onReceive(inputLine)
        } catch {
          case e: Exception => System.err.println(e.toString)
        }
      }
    }
  }

  private def findPortIdentifier: CommPortIdentifier = {
    //default port names for apple, raspberry, linux or windows
    val PORT_NAMES = List("/dev/tty.usbserial-A9007UX1", "/dev/ttyACM0", "/dev/ttyUSB0", "COM3")

    val portEnum = CommPortIdentifier.getPortIdentifiers
    while (portEnum.hasMoreElements) {
      val currPortId: CommPortIdentifier = portEnum.nextElement.asInstanceOf[CommPortIdentifier]
      if (PORT_NAMES.contains(currPortId.getName)) {
        return currPortId
      }
    }
    throw new RuntimeException("Could not find COM port.")
  }

  System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0")

  private val portId = findPortIdentifier
  private val timeout = 2000 // Milliseconds to block while waiting for port open
  private val serialPort = portId.open(getClass.getName, timeout).asInstanceOf[SerialPort]
  private val DATA_RATE = 9600 // Default bits per second for COM port.
  serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)
  private val input = new BufferedReader(new InputStreamReader(serialPort.getInputStream))
  private val output = serialPort.getOutputStream
  serialPort.addEventListener(EventListener)
  serialPort.notifyOnDataAvailable(true)

  /**
    * This should be called when you stop using the port.
    * This will prevent port locking on platforms like Linux.
    */
  def close() {
    serialPort.removeEventListener()
    serialPort.close()
  }

  def sendToArduino(x: String) = {
    output.write(x.getBytes)
    output.flush()
  }
}
