import java.io.FileOutputStream

import com.itextpdf.text._
import com.itextpdf.text.pdf.{PdfPCell, PdfPTable, PdfWriter}

class PDFCreator(stdName: String, stdMobile: String, stdPdfPath: String, courseName: String,
                 invoiceGenerationType: Int) {

  val myDoc = new Document()
  val myWriter = PdfWriter.getInstance(myDoc,new FileOutputStream(stdPdfPath))
  myDoc.open()

  var subject: String = ""

  val myTwoCellCenteredTable1 = new PdfPTable(2)
  myTwoCellCenteredTable1.setTotalWidth(520f)
  myTwoCellCenteredTable1.setLockedWidth(true)

  val myTwoCellCenteredTable2 = new PdfPTable(2)
  myTwoCellCenteredTable2.setTotalWidth(520f)
  myTwoCellCenteredTable2.setLockedWidth(true)

  //METHOD WHICH CREATES CELL AND APPLIES OUR CUSTOMIZED PROPERTIES
  def createPDFCell(cellValue: String, FontType: Font = StandardParameters.normalFont, addBgrdGryColor: Boolean = false,
                    alignmentType: Int, cellBorderReq: Boolean = true) = {

    val myCell = new PdfPCell(new Phrase(cellValue, FontType))
    if(addBgrdGryColor == true) myCell.setBackgroundColor(BaseColor.LIGHT_GRAY)
    myCell.setFixedHeight(14f)
    alignmentType match{
      case 1 => myCell.setHorizontalAlignment(Element.ALIGN_LEFT)
      case 2 => myCell.setHorizontalAlignment(Element.ALIGN_CENTER)
      case 3 => myCell.setHorizontalAlignment(Element.ALIGN_RIGHT)
    }
    if(cellBorderReq == false) {myCell.setBorder(Rectangle.NO_BORDER)}
    else myCell.setBorderWidth(0.7f)

    if(alignmentType == 1) myCell.setLeading(2f, 0.5f)
    else myCell.setLeading(1f, 0.7f)

    myCell
  }

  //METHOD WHICH CREATES PARAGRAPH AND APPLIES OUR CUSTOMIZED PROPERTIES
  def createParagraph(Input: String, FontType: Font = StandardParameters.normalFont, alignmentType: Int) = {
    val myParagraph = new Paragraph(Input,FontType)
    alignmentType match{
      case 1 => myParagraph.setAlignment(Element.ALIGN_LEFT)
      case 2 => myParagraph.setAlignment(Element.ALIGN_CENTER)
      case 3 => myParagraph.setAlignment(Element.ALIGN_RIGHT)
    }
    myParagraph
  }

  val myImage = Image.getInstance(InvoiceCreation.ImagePath)

  myImage.scaleAbsoluteHeight(120f)
  myImage.scaleAbsoluteWidth(270f)
  myImage.setAlignment(Element.ALIGN_LEFT)

  myTwoCellCenteredTable1.addCell(createPDFCell(" ",alignmentType = 1, cellBorderReq=false))
  myTwoCellCenteredTable1.addCell(createPDFCell(" ",alignmentType = 3, cellBorderReq=false))
  myTwoCellCenteredTable1.addCell(createPDFCell(StandardParameters.address1, alignmentType = 1, cellBorderReq=false))
  myTwoCellCenteredTable1.addCell(createPDFCell(StandardParameters.currentDate,alignmentType = 3, cellBorderReq=false,FontType = StandardParameters.normalBoldFont))
  myTwoCellCenteredTable1.addCell(createPDFCell(StandardParameters.address2,alignmentType = 1, cellBorderReq=false))
  myTwoCellCenteredTable1.addCell(createPDFCell(StandardParameters.invoiceFormat,alignmentType = 3, cellBorderReq=false,FontType = StandardParameters.normalBoldFont))
  myTwoCellCenteredTable1.addCell(createPDFCell(StandardParameters.gstNo,alignmentType = 1, cellBorderReq=false,FontType = StandardParameters.normalBoldFont))
  myTwoCellCenteredTable1.addCell(createPDFCell(" ",alignmentType = 3, cellBorderReq=false,FontType = StandardParameters.normalBoldFont))
  myTwoCellCenteredTable1.addCell(createPDFCell(" ",alignmentType = 1, cellBorderReq=false))
  myTwoCellCenteredTable1.addCell(createPDFCell(" ",alignmentType = 3, cellBorderReq=false))

  myTwoCellCenteredTable2.addCell(createPDFCell(StandardParameters.phoneNumber, alignmentType = 1, cellBorderReq = false))
  myTwoCellCenteredTable2.addCell(createPDFCell(StandardParameters.invoiceTo, alignmentType = 3, cellBorderReq = false, FontType = StandardParameters.normalBoldFont))
  myTwoCellCenteredTable2.addCell(createPDFCell(StandardParameters.emailID, alignmentType = 1, cellBorderReq = false))
  myTwoCellCenteredTable2.addCell(createPDFCell(stdName, alignmentType = 3, cellBorderReq = false))

  val myPaymentTable = new PdfPTable(if(invoiceGenerationType == 1) 5 else 4)

  // WHEN IT COMES WITH 2 TYPES OF GENERATION THE PAYMENT TABLE FIELDS VARY FOR BOTH OF THEM SO WE USED SWITCH
  // STATEMENT FOR IT
  invoiceGenerationType match{
    case 1 => {
                // PDF GENERATION FOR EVERY INSTALLMENT
                myPaymentTable.setTotalWidth(PageSize.A4.getWidth())
                myPaymentTable.setLockedWidth(true)
                myPaymentTable.setTotalWidth(520)

                myPaymentTable.addCell(createPDFCell(StandardParameters.courseHeaderNumber,
                  FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

                myPaymentTable.addCell(createPDFCell(StandardParameters.courseHeaderName,
                  FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

                myPaymentTable.addCell(createPDFCell(StandardParameters.qtyHeader,
                  FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

                myPaymentTable.addCell(createPDFCell(StandardParameters.unitPrice,
                  FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

                myPaymentTable.addCell(createPDFCell(StandardParameters.totalPrice,
                  FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))
              }
    case 2 => {
              // PDF GENERATION FOR EVERY STUDENT PER COURSE
                myPaymentTable.setTotalWidth(PageSize.A4.getWidth())
                myPaymentTable.setLockedWidth(true)
                myPaymentTable.setTotalWidth(520)

                myPaymentTable.addCell(createPDFCell(StandardParameters.courseHeaderNumber,
                        FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

                myPaymentTable.addCell(createPDFCell(StandardParameters.courseHeaderName,
                        FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

                myPaymentTable.addCell(createPDFCell(StandardParameters.installmentDate,
                        FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

                myPaymentTable.addCell(createPDFCell(StandardParameters.installmentAmount,
                        FontType = StandardParameters.normalBoldFont,alignmentType = 2,addBgrdGryColor = true))

              }
  }

  // INSERTING OF DATA INTO PAYMENT TABLE i.e., HOW MUCH AMOUNT PAID AT WHICH MONTH
  def insertInstallments(invoiceSeq: Int, invoiceDate: String, invoiceAmount: Float, totalPrice: Float) ={
    myPaymentTable.addCell(createPDFCell(invoiceSeq.toString,alignmentType = 2))
    myPaymentTable.addCell(createPDFCell(courseName,alignmentType = 2))

    // PDF GENERATION PER EACH INSTALLMENT
    if(invoiceGenerationType == 1){
      myPaymentTable.addCell(createPDFCell("1",alignmentType = 2))
      myPaymentTable.addCell(createPDFCell(invoiceAmount.toString,alignmentType = 2))
      myPaymentTable.addCell(createPDFCell(totalPrice.toString,alignmentType = 2))

      subject = s"Please find the receipt of your Invoice for the month of $invoiceDate, paid as Installment : ${invoiceSeq} of " +
        s"the below course."
    }else{    // PDF GENERATION PER EACH STUDENT PER COURSE
      myPaymentTable.addCell(createPDFCell(invoiceDate.toString,alignmentType = 2))
      myPaymentTable.addCell(createPDFCell(invoiceAmount.toString,alignmentType = 2))

      subject = s"Please find the receipt of your Invoice for all the Installment for the below course."
    }
  }

  // FINAL METHOD TO COMMIT THE DATA ONTO THE PDF FILE
  def loadDataIntoPDF(totalAmountPaid: Float) = {
    val gstAmount: Float = (totalAmountPaid*18)/100f

    myDoc.add(myImage)
    myDoc.add(createParagraph(Input = StandardParameters.invoiceHeader,FontType = StandardParameters.headerBoldFont,alignmentType = 3))
    myDoc.add(myTwoCellCenteredTable1)

    myDoc.add(myTwoCellCenteredTable2)
    myDoc.add(Chunk.NEWLINE)

    myDoc.add(createParagraph(s"Customer Mobile Number : $stdMobile",alignmentType = 3))
    myDoc.add(createParagraph(s"Dear $stdName,", alignmentType = 1))
    myDoc.add(createParagraph(subject, alignmentType = 2))
    myDoc.add(Chunk.NEWLINE)

    myDoc.add(myPaymentTable)
    myDoc.add(Chunk.NEWLINE)

    myDoc.add(createParagraph(s"Subtotal   : ${(totalAmountPaid-gstAmount).toString}",alignmentType = 3, FontType = StandardParameters.normalBoldFont))
    myDoc.add(createParagraph(s"GST(18%) : ${gstAmount.toString}",alignmentType = 3, FontType = StandardParameters.normalBoldFont))
    myDoc.add(createParagraph(s"Total      : ${totalAmountPaid.toString}",alignmentType = 3, FontType = StandardParameters.normalBoldFont))

    myDoc.add(Chunk.NEWLINE)
    myDoc.add(createParagraph(StandardParameters.regards, alignmentType = 3))
    myDoc.add(createParagraph(StandardParameters.companyName, alignmentType = 3,FontType = StandardParameters.normalBoldFont))
    myDoc.add(Chunk.NEWLINE)
    myDoc.add(createParagraph(StandardParameters.note,alignmentType = 2))

    myDoc.close()
    myWriter.close()
  }
}
