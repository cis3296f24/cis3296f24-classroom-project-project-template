#Tests file for main.py in WhiteboardApplication directory
#Created by Riviera Sperduto
#Created 11/19/2024
#Last edit: 11/19/2024
from time import sleep

import pytest

import WhiteboardApplication.main
from WhiteboardApplication.main import *
from pytestqt import *

app = QApplication(sys.argv)


def test_AppCreation():
    print("Hello World")


    window = WhiteboardApplication.main.MainWindow()
    window.show()

    sleep(2)

    assert window is not None

def test_PenButton(qtbot):

    #app = QApplication(sys.argv)

    window = WhiteboardApplication.main.MainWindow()
    window.show()

    sleep(2)
    #this is prob gonna be ugly...

    qtbot.addWidget(window)



    pen = MainWindow().tb_actionPen.triggered

    assert True