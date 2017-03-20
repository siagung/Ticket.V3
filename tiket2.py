#!/usr/bin/python

# import core
#import sys, string
#import subprocess
import sys
#from datetime import datetime
from escpos.printer import Usb
Epson = printer.Usb(0x04b8,0x0e11)

#Epson.text('\x1D\x62\x01')
#Epson.hw("INIT")
Epson.control("LF")
Epson.control("LF")
Epson.set('CENTER','A','normal',1,1)
#Epson.text(datetime.strptime(date_string, "%Y-%m-%d")+"\r\n")
Epson.control("LF")
Epson.set('CENTER','A','normal',1,1)
Epson.text("Nomor Antrian Anda :\r\n")
Epson.control("LF")
Epson.set('CENTER','A','normal',2,1)
#if sys.argv[1] = 1:
Epson.text("P E M B A Y A R A N\r\n")
#else:
#Epson.text("L A Y A N A N\r\n")
Epson.control("LF")
Epson.set('CENTER','B','B',2,1)
#Epson.text(sys.argv[2])
Epson.text("1-001")
Epson.control("LF")
Epson.control("LF")
Epson.control("LF")
Epson.set('CENTER','A','normal',1,1)
Epson.text("Mohon menunggu hingga nomor dipanggil...\r\n")
#Epson.text("yang belum dipanggil 8")

Epson.cut()


