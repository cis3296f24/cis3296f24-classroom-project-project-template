#Tests file for main.py in WhiteboardApplication directory
#Created by Riviera Sperduto
#Created 11/19/2024
#Last edit: 11/19/2024
from time import sleep

import pytest

import WhiteboardApplication.main
from WhiteboardApplication.main import *
from pytestqt import *


def test_AppCreation():
    print("Hello World")
    app = QApplication(sys.argv)

    window = WhiteboardApplication.main.MainWindow()
    window.show()

    sleep(5)

    app.exit()
    assert window is not None