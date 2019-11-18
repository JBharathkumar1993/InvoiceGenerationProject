import java.io.File

import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import scala.collection.mutable.ArrayBuffer

/*
  STEP - 1 ==> IN ORDER TO WORK ON WITH XLSX FILE WE NEED TO IMPORT XSSFWORKBOOK CLASS FROM APACHE POI.
               SO WE ARE PASSING THE PATH OF XLSX FILE TO THE WORKBOOK
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 2 ==> IN EXCEL FILE WE HAVE DATA IN THE FORM OF TABULAR FORMAT IN SHEETS. SO IN ORDER TO ACCESS DATA FIRST
               WE NEED TO GET THE SHEET OF FILE @ Oth INDEX(FIRST SHEET)
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 3 ==> WE HAVE "getLastRowNum" METHOD WHICH GIVES YOU THE NUMBER OF ROWS(STUDENT'S PAYMENTS DONE FOR COURSE)
               AVAILABLE IN THE SHEET
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 4 ==> IN ORDER TO STORE EACH ROW INFO AS COLLECTION OBJECT WE ARE USING ARRAYBUFFER WHICH IS MUTABLE AS WE
               NEED TO ADD ELEMENT ROW BY ROW INTO THE COLLECTION OBJECT.
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 5 ==> DECLARED A VARIABLE TO STORE THE NUMBER OF "HEADER COLUMNS" DOES THE DATA HAS
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 6 ==> GENERATING AN ARRAY WITH RANGE OF ROW NUMBER OF ELEMENTS. IF EXCEL HAS 10 ROWS IT GENERATES
               ARRAY(0,1,2,3,....10)
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 7 ==> USING FUNCTIONAL PROGRAMMING LOOPING EACH ROW WITH THE HELP OF "eachRow" VARIABLE
  STEP - 8 ==> GETTING ALL THE COLUMNS INFORMATION OF EACH ROW
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 9 ==> CREATED AN INTERMEDIATE VARIABLE TO HOLD EACH COLUMN CONTENT OF ONE ROW AS ELEMENTS IN AN ARRAY BUFFER
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 10 ==> "eachRow = 0" MEANS IT IS THE HEADER ROW, WE CAN CONFIRM THE NUMBER OF COLUMNS ONLY THROUGH HEADER
                ROW
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 11 ==> LOOPING THROUGH EACH COLUMN CELL OF ONE ROW TO GET THE DATA CORRESPONDINGLY WHERE "eachColumn"
                REPRESENTS CELL INDEX
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 12 ==> SOMETIMES WE MAY GET A COLUMN CELL AS EMPTY WHERE NO TRANSACTION WAS DONE THEN WE WILL BE PLACING AN
                EMPTY VALUE TO THE COLLECTION ELEMENT
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 13 ==> WE USE DATAFORMATTER CLASS WE CONVERTS EVERY CELL DATA TO STRING TYPE IRRESPECTIVE OF ANY TYPE BECAUSE
                WHILE READING THE DATA SUPPOSE IF PHONE NUMBER IS 9999999999 THEN IT IS TREATED AS "9.89E4" AS BIGINT
                SO IN ORDER TO OVERCOME THOSE ISSUES WE CONVERTED TO A SINGLE TYPE
  ----------------------------------------------------------------------------------------------------------------------
  STEP - 14 ==> FINALLY APPENDING COLUMN COLLECTION OF ARRAYBUFFER TO THE MAIN ROW ARRAYBUFFER SO THE FINAL ARRAYBUFFER
                TYPE WOULD BE ARRAYBUFFER[ARRAYBUFFER[STRING]]
                --> INTERNAL ARRAYBUFFER IS THE COLUMN CONTENT
                --> OUTER ARRAYBUFFER IS THE ROW CONTENT

*/

class ExcelltoArrayConverter(InputPath: String){

  val MyWorkBook = new XSSFWorkbook(new File(InputPath))                                                /* STEP - 1 */
  val MySheet = MyWorkBook.getSheetAt(0)                                                         /* STEP - 2 */
  val RowCount = MySheet.getLastRowNum    //IF EXCEL HAS 3 ROWS THEN ROWCOUNT = 2 WHICH STARTS FROM 0   /* STEP - 3 */

  val RowDataHolder: ArrayBuffer[ArrayBuffer[String]] = ArrayBuffer()                                   /* STEP - 4 */
  var MaxColumnNumber: Int = _                                                                          /* STEP - 5 */

  // ROW LOOP
  (0 to RowCount).toArray.foreach(eachRow => {                                                       /* STEP - 6 */

    val ColumnDataHolder = MySheet.getRow(eachRow)                                                      /* STEP - 7 */
    val IntermediateArray = new ArrayBuffer[String]()                                                   /* STEP - 8 */
    if(eachRow == 0) {MaxColumnNumber = ColumnDataHolder.getLastCellNum}                                /* STEP - 9 */                      /* STEP - 11 */

    // COLUMN LOOP FOR EACH ROW
    (0 until MaxColumnNumber).toArray.foreach(eachColumn => {                                          /* STEP - 10 */                                                     /* STEP - 12 */

      if(ColumnDataHolder.getCell(eachColumn) == null) {IntermediateArray += ""}                        /* STEP - 11 */
      else {
        IntermediateArray += new DataFormatter().formatCellValue(ColumnDataHolder.getCell(eachColumn))  /* STEP - 12 */
      }
    })

    RowDataHolder ++= ArrayBuffer(IntermediateArray)                                                    /* STEP - 13 */
  })
}