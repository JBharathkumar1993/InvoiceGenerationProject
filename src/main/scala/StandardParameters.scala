
import java.text.SimpleDateFormat
import java.util.Calendar
import com.itextpdf.text.Font

object StandardParameters {
  val invoiceHeader: String = "INVOICE"
  val address1: String = "3rd Floor, Plot #5"
  val address2: String = "Venkanna Hills, Chintal, Quthubullapur, Hyderabad, 500055"
  val gstNo: String = "GSTIN No : 36AACCO7284M1ZU"
  val formatDate = new SimpleDateFormat("dd-MMM-yyyy")
  val currentDate = formatDate.format(Calendar.getInstance.getTime)
  val invoiceFormat: String = "Invoice # OLC-002"
  val phoneNumber: String = "Phone : +91- 7 999 01 02 03"
  val emailID: String = "info@onlinelearningcenter.in"
  val invoiceTo: String = "Invoice to"
  val customerMobile: String = "Customer Mobile Number"
  val regards: String = "Looking Forward,"
  val companyName: String = "Online Learning Center Pvt Ltd"
  val subTotal: String = "Subtotal"
  val gstPercent: String = "GST (18%)"
  val total: String = "Total"
  val note: String = "This is an e-bill and does not need any signature"
  val normalFont: Font = new Font(Font.FontFamily.HELVETICA,9.5f)
  val normalBoldFont: Font = new Font(Font.FontFamily.HELVETICA,9.5f,Font.BOLD)
  val headerBoldFont: Font = new Font(Font.FontFamily.HELVETICA,15f,Font.BOLD)
  val courseHeaderNumber: String = "#"
  val courseHeaderName: String = "Course Name"
  val qtyHeader: String = "Qty"
  val unitPrice: String = "Unit Price(INR)"
  val totalPrice: String = "Total (INR)"
  val installmentDate: String = "Installement Date"
  val installmentAmount: String = "Amount"
  val invoiceDescription: String = "Please find the receipt of your Invoice for the Course "
  }
