from PySide6.QtCore import Qt, QSize, QRect
from PySide6.QtGui import QCursor
from PySide6.QtWidgets import QAbstractScrollArea, QSizePolicy, QGraphicsView, QHBoxLayout, QWidget, QScrollArea, \
    QGridLayout


class NewNotebook:
    def add_new_notebook(self):
        self.notebook = QWidget()
        self.notebook.setObjectName(u"notebook")
        self.notebook.setMinimumSize(QSize(850, 0))
        self.notebook.setStyleSheet(u"")

        self.gridLayout = QGridLayout()
        self.gridLayout.setObjectName(u"gridLayout")
        self.gridLayout.setContentsMargins(0, 0, 0, 0)

        self.scrollArea_2 = QScrollArea()
        self.scrollArea_2.setObjectName(u"scrollArea_2")
        self.scrollArea_2.setMinimumSize(QSize(0, 0))
        self.scrollArea_2.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded)
        self.scrollArea_2.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded)
        self.scrollArea_2.setSizeAdjustPolicy(QAbstractScrollArea.SizeAdjustPolicy.AdjustIgnored)
        self.scrollArea_2.setWidgetResizable(True)

        self.gridLayout.addWidget(self.scrollArea_2)

        self.scrollAreaWidgetContents_3 = QWidget()
        self.scrollAreaWidgetContents_3.setObjectName(u"scrollAreaWidgetContents_3")
        self.scrollAreaWidgetContents_3.setGeometry(QRect(0, 0, 850, 1100))
        self.scrollAreaWidgetContents_3.setMinimumSize(QSize(850, 1100))
        self.scrollAreaWidgetContents_3.setMaximumSize(QSize(850, 1100))

        self.scrollArea_2.setWidget(self.scrollAreaWidgetContents_3)

        self.horizontalLayout_2 = QHBoxLayout()
        self.horizontalLayout_2.setObjectName(u"horizontalLayout_2")
        self.horizontalLayout_2.setContentsMargins(0, 0, 0, 0)

        self.scrollAreaWidgetContents_3.setLayout(self.horizontalLayout_2)

        self.gv_Canvas = QGraphicsView()
        self.gv_Canvas.setObjectName(u"gv_Canvas")
        sizePolicy1 = QSizePolicy(QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)
        sizePolicy1.setHorizontalStretch(0)
        sizePolicy1.setVerticalStretch(0)
        sizePolicy1.setHeightForWidth(self.gv_Canvas.sizePolicy().hasHeightForWidth())
        self.gv_Canvas.setSizePolicy(sizePolicy1)
        self.gv_Canvas.setMinimumSize(QSize(850, 1100))
        self.gv_Canvas.viewport().setProperty(u"cursor", QCursor(Qt.CursorShape.ArrowCursor))
        self.gv_Canvas.setStyleSheet(u"border:0px;")
        self.gv_Canvas.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded)
        self.gv_Canvas.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded)
        self.gv_Canvas.setSizeAdjustPolicy(QAbstractScrollArea.SizeAdjustPolicy.AdjustIgnored)

        self.horizontalLayout_2.addWidget(self.gv_Canvas)

        self.notebook.setLayout(self.gridLayout)

        return self.notebook

    def get_canvas(self):
        return self.gv_Canvas