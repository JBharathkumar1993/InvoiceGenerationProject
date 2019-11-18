import java.io.{File, FileNotFoundException, IOException}
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

object InvoiceCreation {

  print("Enter Image Path : ")
  val ImagePath = StdIn.readLine()  //"C:\\Users\\my pc\\Desktop\\OLC.jpg"

  print("Enter Output Folder Path : ")
  val OutputFolder = StdIn.readLine()   //"C:\\Users\\my pc\\Desktop"

  print("Enter Input Excel File Path : ")
  val InputPath = StdIn.readLine()  //"C:\\Users\\my pc\\Desktop\\invoice.xlsx"

  print("Enter type of Invoice Generation 1 -> Per Installment, 2 -> Per Course : ")
  val typeOfInvoiceGeneration: Int = StdIn.readLine().toInt //

  def createInVoicePerInstallment(typeOfGeneration: Int) = {
    try{

      val MyArrayData: ArrayBuffer[ArrayBuffer[String]] = new ExcelltoArrayConverter(InputPath).RowDataHolder
      var StdName: String = ""; var StdEmail: String = ""; var StdMobile: String = ""
      var totalPaid: Float = 0f; var CourseName: String = ""; var TotalCourseFee: String = ""
      var StdId: Int = 0
      val FinalPayments = MyArrayData.tail    // excluding the header
      var OutputPath: String = ""

      /* LOOPING FOR EACH ROW */
      FinalPayments.foreach(x => {

        StdId = x(0).toInt;        StdName = x(1);            StdEmail = x(2);
        StdMobile = x(3);          CourseName = x(5);         TotalCourseFee = x(6)

        /* IF PDF GENERATION HAS TO BE DONE ACCORDING TO INSTALLMENT THEN CASE - 1 WILL BE EXECUTED */
        typeOfInvoiceGeneration match{
          case 1 => {
                      /* USING SLICE GETTING THE INSTALLMENT AMOUNTS, USING SLIDING CLUBBING SIMILAR INSTALLMENT PAYMENTS */
                      x.slice(7, x.length).sliding(2, 2).zipWithIndex.foreach { case (x, y) => {
                        if (x(0).length > 1) {
                          OutputPath = OutputFolder + s"\\OutputFiles\\${StdName}\\\\${x(1)}.pdf"
                          val MyFile = new File(OutputPath)
                          MyFile.getParentFile.mkdirs() // CREATING PARENT FOLDERS IF NOT CREATED

                          val myReceipt = new PDFCreator(StdName, StdMobile, OutputPath, CourseName, typeOfInvoiceGeneration)
                          myReceipt.insertInstallments((y + 1), x(1), x(0).toFloat,TotalCourseFee.toFloat)
                          myReceipt.loadDataIntoPDF(x(0).toFloat)   // COMMITTING DATA ONTO THE PDF
                        }
                      }}
                    }
          /* IF PDF GENERATION HAS TO BE DONE PER COURSE PER STUDENT THEN CASE - 2 WILL BE EXECUTED */
          case 2 => {
                      OutputPath = OutputFolder + s"\\OutputFiles2\\$StdName - $StdId.pdf"
                      val MyFile = new File(OutputPath)
                      totalPaid = 0
                      MyFile.getParentFile.mkdirs()     // CREATING PARENT FOLDERS IF NOT CREATED
                      val myReceipt = new PDFCreator(StdName, StdMobile, OutputPath, CourseName, typeOfInvoiceGeneration)

                      /* USING SLICE GETTING THE INSTALLMENT AMOUNTS, USING SLIDING CLUBBING SIMILAR INSTALLMENT PAYMENTS */
                      x.slice(7, x.length).sliding(2, 2).zipWithIndex.foreach {case (x, y) => {
                        if (x(0).length > 1) {
                          totalPaid += x(0).toFloat
                          myReceipt.insertInstallments((y + 1), x(1), x(0).toFloat,TotalCourseFee.toFloat)
                        }
                      }}
                      myReceipt.loadDataIntoPDF(totalPaid)   // COMMITTING DATA ONTO THE PDF
                      totalPaid = 0
                    }
        }
      })
    }catch{
      case e: ClassNotFoundException => println(s"Error Found : $e")
      case e: FileNotFoundException => println(s"Error Found : File Not Found")
      case e: IOException => println(s"Error Found : Input Output Exception")
      case e: Exception => println(s"Other Exception : $e")
    }
  }

  def main(args: Array[String]) = {
    createInVoicePerInstallment(typeOfInvoiceGeneration)
  }
}
