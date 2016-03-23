package hackathon.pi.api

import java.io.File
import javax.sound.sampled.{AudioInputStream, AudioSystem, Clip}

object Geluid {
  def makeSound: Unit = {
    val file: File = new File("lion-roar2.wav")
    var audioIn: AudioInputStream = null
    try {
      audioIn = AudioSystem.getAudioInputStream(file)
      var clip: Clip = null
      clip = AudioSystem.getClip
      clip.open(audioIn)
      clip.start
      //      Thread.sleep(clip.getMicrosecondLength / 3000)
    }
    catch {
      case e: Exception => {
        e.printStackTrace
      }
    }
  }

}
