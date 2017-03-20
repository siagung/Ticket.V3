import sys
import locale

from datetime import datetime
from escpos.printer import *


""" Seiko Epson Corp. Receipt Printer M129 Definitions (EPSON TM-T88IV) """
Epson = Usb(0x04b8,0x0e11,0)
Epson.set('CENTER','A','NORMAL',1,1)
Epson.text("Hello World\n")
#p.image("logo.gif")
#Epson.barcode('1324354657687','EAN13',64,2,'','')
#Epson.text('\x1D\x62\x01')
#Epson.hw("INIT")
Epson.set('CENTER','A','NORMAL',1,1)
#Epson.text(datetime.strptime(date_string, "%Y-%m-%d")+"\r\n")
Epson.text("01-09-2016 09:45:23\r\n")
Epson.set('CENTER','A','NORMAL',2,1)
Epson.text("Nomor Antrian Anda :\r\n\n")
#Epson.set('CENTER','A','normal',2,1)
#if sys.argv[1] = 1:
#Epson.text("P E M B A Y A R A N\r\n")
#else:
#Epson.text("L A Y A N A N\r\n")
Epson.set('CENTER','B','NORMAL',4,4)
#Epson.text(sys.argv[2])
Epson.text("1-001\n\n")
Epson.set('CENTER','A','NORMAL',1,1)
Epson.text("Mohon menunggu hingga nomor dipanggil...\r\n")
#Epson.text("yang belum dipanggil 8")

Epson.cut()
