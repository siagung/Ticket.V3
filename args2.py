import sys
from escpos.printer import *
import datetime
import locale

current = datetime.datetime.now()


tahun = current.year
bulan = current.month
hari = current.day

jam = current.hour
menit = current.minute
detik = current.second
date_string = "{}-{}-{} {}:{}:{}".format(hari, bulan, tahun,jam,menit,detik)

""" Seiko Epson Corp. Receipt Printer M129 Definitions (EPSON TM-T88IV) """
Epson = Usb(0x04b8,0x0e11,0)
Epson.set('CENTER','A','NORMAL',1,1)
#Epson.text("Hello World\n")
#p.image("logo.gif")
#Epson.barcode('1324354657687','EAN13',64,2,'','')
#Epson.text('\x1D\x62\x01')
#Epson.hw("INIT")
Epson.set('CENTER','A','NORMAL',1,1)
Epson.text(date_string+"\r\n")
#Epson.text("01-09-2016 09:45:23\r\n")
#Epson.set('CENTER','A','NORMAL',1,2)
Epson.text("Nomor Antrian Anda :\r\n\n")
Epson.set('CENTER','A','normal',1,2)
if (sys.argv[1] == "NPWP"):
 Epson.text("N P W P\r\n\n")
elif (sys.argv[1] == "SURAT_LAIN-LAIN"):
 Epson.text("LAIN-LAINNYA\r\n\n")
else :
 Epson.text("SPT MASA DAN SPT TAHUNAN\r\n\n")
Epson.set('CENTER','B','NORMAL',4,4)
Epson.text(sys.argv[2]+"\n\n")
#Epson.text("1-001\n\n")
Epson.set('CENTER','A','NORMAL',1,1)
Epson.text("Terimakasih atas Kunjungan Anda\r\n")
Epson.text("Silahkan menunggu nomor Anda dipanggil\r\n")
#Epson.text("yang belum dipanggil 8")

Epson.cut()
