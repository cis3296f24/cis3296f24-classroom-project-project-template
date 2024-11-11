# -*- coding: utf-8 -*-

################################################################################
## Form generated from reading UI file 'boardNLRQPL.ui'
##
## Created by: Qt User Interface Compiler version 6.8.0
##
## WARNING! All changes made in this file will be lost when recompiling UI file!
################################################################################

from PySide6.QtCore import (QCoreApplication, QDate, QDateTime, QLocale,
    QMetaObject, QObject, QPoint, QRect,
    QSize, QTime, QUrl, Qt)
from PySide6.QtGui import (QAction, QBrush, QColor, QConicalGradient,
    QCursor, QFont, QFontDatabase, QGradient,
    QIcon, QImage, QKeySequence, QLinearGradient,
    QPainter, QPalette, QPixmap, QRadialGradient,
    QTransform)
from PySide6.QtWidgets import (QAbstractScrollArea, QApplication, QDial, QGraphicsView,
    QGridLayout, QHBoxLayout, QMainWindow, QMenu,
    QMenuBar, QPushButton, QScrollArea, QSizePolicy,
    QSpacerItem, QStatusBar, QTabWidget, QToolBar,
    QToolButton, QVBoxLayout, QWidget)
import Icons_rc

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        if not MainWindow.objectName():
            MainWindow.setObjectName(u"MainWindow")
        MainWindow.resize(1060, 702)
        sizePolicy = QSizePolicy(QSizePolicy.Policy.Preferred, QSizePolicy.Policy.Maximum)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(MainWindow.sizePolicy().hasHeightForWidth())
        MainWindow.setSizePolicy(sizePolicy)
        MainWindow.setStyleSheet(u"")
        self.actionOpen = QAction(MainWindow)
        self.actionOpen.setObjectName(u"actionOpen")
        self.actionSave = QAction(MainWindow)
        self.actionSave.setObjectName(u"actionSave")
        self.actionSave_As = QAction(MainWindow)
        self.actionSave_As.setObjectName(u"actionSave_As")
        self.actionClear = QAction(MainWindow)
        self.actionClear.setObjectName(u"actionClear")
        self.actionClose = QAction(MainWindow)
        self.actionClose.setObjectName(u"actionClose")
        self.actionTemplates = QAction(MainWindow)
        self.actionTemplates.setObjectName(u"actionTemplates")
        self.actionShare = QAction(MainWindow)
        self.actionShare.setObjectName(u"actionShare")
        self.actionExport = QAction(MainWindow)
        self.actionExport.setObjectName(u"actionExport")
        self.actionImport = QAction(MainWindow)
        self.actionImport.setObjectName(u"actionImport")
        self.actionDocument = QAction(MainWindow)
        self.actionDocument.setObjectName(u"actionDocument")
        self.actionHost = QAction(MainWindow)
        self.actionHost.setObjectName(u"actionHost")
        self.actionJoin = QAction(MainWindow)
        self.actionJoin.setObjectName(u"actionJoin")
        self.actionCut = QAction(MainWindow)
        self.actionCut.setObjectName(u"actionCut")
        self.actionCopy = QAction(MainWindow)
        self.actionCopy.setObjectName(u"actionCopy")
        self.actionPaste = QAction(MainWindow)
        self.actionPaste.setObjectName(u"actionPaste")
        self.actionSelect_All = QAction(MainWindow)
        self.actionSelect_All.setObjectName(u"actionSelect_All")
        self.actionDelete = QAction(MainWindow)
        self.actionDelete.setObjectName(u"actionDelete")
        self.actionUndo = QAction(MainWindow)
        self.actionUndo.setObjectName(u"actionUndo")
        self.actionRedo = QAction(MainWindow)
        self.actionRedo.setObjectName(u"actionRedo")
        self.toolbar_actionCursor = QAction(MainWindow)
        self.toolbar_actionCursor.setObjectName(u"toolbar_actionCursor")
        self.toolbar_actionCursor.setCheckable(True)
        icon = QIcon()
        icon.addFile(u":/Tools/cursor.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionCursor.setIcon(icon)
        self.toolbar_actionPen = QAction(MainWindow)
        self.toolbar_actionPen.setObjectName(u"toolbar_actionPen")
        self.toolbar_actionPen.setCheckable(True)
        icon1 = QIcon()
        icon1.addFile(u":/Tools/pen.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionPen.setIcon(icon1)
        self.toolbar_actionHighlighter = QAction(MainWindow)
        self.toolbar_actionHighlighter.setObjectName(u"toolbar_actionHighlighter")
        self.toolbar_actionHighlighter.setCheckable(True)
        icon2 = QIcon()
        icon2.addFile(u":/Tools/highlighter.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionHighlighter.setIcon(icon2)
        self.toolbar_actionEraser = QAction(MainWindow)
        self.toolbar_actionEraser.setObjectName(u"toolbar_actionEraser")
        self.toolbar_actionEraser.setCheckable(True)
        icon3 = QIcon()
        icon3.addFile(u":/Tools/eraser.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionEraser.setIcon(icon3)
        self.toolbar_actionLine = QAction(MainWindow)
        self.toolbar_actionLine.setObjectName(u"toolbar_actionLine")
        self.toolbar_actionLine.setCheckable(True)
        icon4 = QIcon()
        icon4.addFile(u":/Tools/line.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionLine.setIcon(icon4)
        self.toolbar_actionShapes = QAction(MainWindow)
        self.toolbar_actionShapes.setObjectName(u"toolbar_actionShapes")
        self.toolbar_actionShapes.setCheckable(True)
        icon5 = QIcon()
        icon5.addFile(u":/Tools/shapes.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionShapes.setIcon(icon5)
        self.toolbar_actionImages = QAction(MainWindow)
        self.toolbar_actionImages.setObjectName(u"toolbar_actionImages")
        self.toolbar_actionImages.setCheckable(True)
        icon6 = QIcon()
        icon6.addFile(u":/Tools/image.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionImages.setIcon(icon6)
        self.toolbar_actionVideos = QAction(MainWindow)
        self.toolbar_actionVideos.setObjectName(u"toolbar_actionVideos")
        self.toolbar_actionVideos.setCheckable(True)
        icon7 = QIcon()
        icon7.addFile(u":/Tools/video.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionVideos.setIcon(icon7)
        self.actionUndo_2 = QAction(MainWindow)
        self.actionUndo_2.setObjectName(u"actionUndo_2")
        icon8 = QIcon()
        icon8.addFile(u":/Tools/undo.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.actionUndo_2.setIcon(icon8)
        self.actionRedo_2 = QAction(MainWindow)
        self.actionRedo_2.setObjectName(u"actionRedo_2")
        icon9 = QIcon()
        icon9.addFile(u":/Tools/redo.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.actionRedo_2.setIcon(icon9)
        self.toolbar_actionSelection = QAction(MainWindow)
        self.toolbar_actionSelection.setObjectName(u"toolbar_actionSelection")
        self.toolbar_actionSelection.setCheckable(True)
        icon10 = QIcon()
        icon10.addFile(u":/Tools/selection.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionSelection.setIcon(icon10)
        self.toolbar_actionText = QAction(MainWindow)
        self.toolbar_actionText.setObjectName(u"toolbar_actionText")
        self.toolbar_actionText.setCheckable(True)
        icon11 = QIcon()
        icon11.addFile(u":/Tools/text.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.toolbar_actionText.setIcon(icon11)
        self.actionChat = QAction(MainWindow)
        self.actionChat.setObjectName(u"actionChat")
        self.actionNew = QAction(MainWindow)
        self.actionNew.setObjectName(u"actionNew")
        self.centralwidget = QWidget(MainWindow)
        self.centralwidget.setObjectName(u"centralwidget")
        self.centralwidget.setStyleSheet(u"")
        self.horizontalLayout = QHBoxLayout(self.centralwidget)
        self.horizontalLayout.setSpacing(0)
        self.horizontalLayout.setObjectName(u"horizontalLayout")
        self.horizontalLayout.setContentsMargins(-1, 5, 0, 0)
        self.w_Tools = QWidget(self.centralwidget)
        self.w_Tools.setObjectName(u"w_Tools")
        self.w_Tools.setMaximumSize(QSize(50, 16777215))
        self.verticalLayout = QVBoxLayout(self.w_Tools)
        self.verticalLayout.setSpacing(0)
        self.verticalLayout.setObjectName(u"verticalLayout")
        self.verticalLayout.setContentsMargins(0, 25, 0, 0)
        self.pb_Cursor = QPushButton(self.w_Tools)
        self.pb_Cursor.setObjectName(u"pb_Cursor")
        self.pb_Cursor.setMinimumSize(QSize(0, 40))
        self.pb_Cursor.setMaximumSize(QSize(60, 60))
        self.pb_Cursor.setStyleSheet(u"")
        self.pb_Cursor.setIcon(icon)
        self.pb_Cursor.setIconSize(QSize(25, 25))
        self.pb_Cursor.setCheckable(True)
        self.pb_Cursor.setChecked(False)
        self.pb_Cursor.setFlat(True)

        self.verticalLayout.addWidget(self.pb_Cursor)

        self.tb_Pen = QToolButton(self.w_Tools)
        self.tb_Pen.setObjectName(u"tb_Pen")
        self.tb_Pen.setMinimumSize(QSize(0, 40))
        self.tb_Pen.setMaximumSize(QSize(60, 60))
        self.tb_Pen.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Pen.setIcon(icon1)
        self.tb_Pen.setIconSize(QSize(25, 25))
        self.tb_Pen.setCheckable(True)
        self.tb_Pen.setPopupMode(QToolButton.ToolButtonPopupMode.DelayedPopup)
        self.tb_Pen.setToolButtonStyle(Qt.ToolButtonStyle.ToolButtonIconOnly)
        self.tb_Pen.setArrowType(Qt.ArrowType.NoArrow)

        self.verticalLayout.addWidget(self.tb_Pen)

        self.tb_Highlighter = QToolButton(self.w_Tools)
        self.tb_Highlighter.setObjectName(u"tb_Highlighter")
        self.tb_Highlighter.setMinimumSize(QSize(0, 40))
        self.tb_Highlighter.setMaximumSize(QSize(60, 60))
        self.tb_Highlighter.setLayoutDirection(Qt.LayoutDirection.LeftToRight)
        self.tb_Highlighter.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Highlighter.setIcon(icon2)
        self.tb_Highlighter.setIconSize(QSize(25, 25))
        self.tb_Highlighter.setCheckable(True)
        self.tb_Highlighter.setPopupMode(QToolButton.ToolButtonPopupMode.DelayedPopup)
        self.tb_Highlighter.setArrowType(Qt.ArrowType.NoArrow)

        self.verticalLayout.addWidget(self.tb_Highlighter)

        self.tb_Line = QToolButton(self.w_Tools)
        self.tb_Line.setObjectName(u"tb_Line")
        self.tb_Line.setMinimumSize(QSize(0, 40))
        self.tb_Line.setMaximumSize(QSize(60, 60))
        self.tb_Line.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Line.setIcon(icon4)
        self.tb_Line.setIconSize(QSize(25, 25))
        self.tb_Line.setCheckable(True)
        self.tb_Line.setPopupMode(QToolButton.ToolButtonPopupMode.DelayedPopup)
        self.tb_Line.setArrowType(Qt.ArrowType.NoArrow)

        self.verticalLayout.addWidget(self.tb_Line)

        self.tb_Select = QToolButton(self.w_Tools)
        self.tb_Select.setObjectName(u"tb_Select")
        self.tb_Select.setMinimumSize(QSize(0, 40))
        self.tb_Select.setMaximumSize(QSize(60, 60))
        self.tb_Select.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Select.setIcon(icon10)
        self.tb_Select.setIconSize(QSize(25, 25))
        self.tb_Select.setPopupMode(QToolButton.ToolButtonPopupMode.DelayedPopup)
        self.tb_Select.setArrowType(Qt.ArrowType.NoArrow)

        self.verticalLayout.addWidget(self.tb_Select)

        self.pb_Eraser = QPushButton(self.w_Tools)
        self.pb_Eraser.setObjectName(u"pb_Eraser")
        self.pb_Eraser.setMinimumSize(QSize(0, 40))
        self.pb_Eraser.setMaximumSize(QSize(60, 60))
        self.pb_Eraser.setIcon(icon3)
        self.pb_Eraser.setIconSize(QSize(25, 25))
        self.pb_Eraser.setCheckable(True)
        self.pb_Eraser.setFlat(True)

        self.verticalLayout.addWidget(self.pb_Eraser)

        self.pb_Text_Button = QPushButton(self.w_Tools)
        self.pb_Text_Button.setObjectName(u"pb_Text_Button")
        self.pb_Text_Button.setMouseTracking(True)
        self.pb_Text_Button.setIcon(icon11)
        self.pb_Text_Button.setIconSize(QSize(25, 25))
        self.pb_Text_Button.setCheckable(True)
        self.pb_Text_Button.setChecked(False)

        self.verticalLayout.addWidget(self.pb_Text_Button)

        self.tb_Text = QToolButton(self.w_Tools)
        self.tb_Text.setObjectName(u"tb_Text")
        self.tb_Text.setMinimumSize(QSize(0, 40))
        self.tb_Text.setMaximumSize(QSize(60, 60))
        self.tb_Text.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Text.setIcon(icon11)
        self.tb_Text.setIconSize(QSize(25, 25))

        self.verticalLayout.addWidget(self.tb_Text)

        self.tb_Shapes = QToolButton(self.w_Tools)
        self.tb_Shapes.setObjectName(u"tb_Shapes")
        self.tb_Shapes.setMinimumSize(QSize(0, 40))
        self.tb_Shapes.setMaximumSize(QSize(60, 60))
        self.tb_Shapes.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Shapes.setIcon(icon5)
        self.tb_Shapes.setIconSize(QSize(25, 25))
        self.tb_Shapes.setPopupMode(QToolButton.ToolButtonPopupMode.DelayedPopup)
        self.tb_Shapes.setArrowType(Qt.ArrowType.NoArrow)

        self.verticalLayout.addWidget(self.tb_Shapes)

        self.tb_Images = QToolButton(self.w_Tools)
        self.tb_Images.setObjectName(u"tb_Images")
        self.tb_Images.setMinimumSize(QSize(0, 40))
        self.tb_Images.setMaximumSize(QSize(60, 60))
        self.tb_Images.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Images.setIcon(icon6)
        self.tb_Images.setIconSize(QSize(25, 25))
        self.tb_Images.setPopupMode(QToolButton.ToolButtonPopupMode.DelayedPopup)
        self.tb_Images.setArrowType(Qt.ArrowType.NoArrow)

        self.verticalLayout.addWidget(self.tb_Images)

        self.tb_Videos = QToolButton(self.w_Tools)
        self.tb_Videos.setObjectName(u"tb_Videos")
        self.tb_Videos.setMinimumSize(QSize(0, 40))
        self.tb_Videos.setMaximumSize(QSize(60, 60))
        self.tb_Videos.setStyleSheet(u"QToolButton {\n"
"	border: none;\n"
"	background: transparent;\n"
"}\n"
"\n"
"QToolButton:hover {\n"
"	background-color: rgb(141, 200, 216);\n"
"	border-radius: 15px;\n"
"}")
        self.tb_Videos.setIcon(icon7)
        self.tb_Videos.setIconSize(QSize(25, 25))
        self.tb_Videos.setPopupMode(QToolButton.ToolButtonPopupMode.DelayedPopup)
        self.tb_Videos.setArrowType(Qt.ArrowType.NoArrow)

        self.verticalLayout.addWidget(self.tb_Videos)

        self.dial = QDial(self.w_Tools)
        self.dial.setObjectName(u"dial")
        self.dial.setMaximumSize(QSize(60, 60))

        self.verticalLayout.addWidget(self.dial)

        self.verticalSpacer = QSpacerItem(20, 40, QSizePolicy.Policy.Minimum, QSizePolicy.Policy.Expanding)

        self.verticalLayout.addItem(self.verticalSpacer)

        self.pb_Undo = QPushButton(self.w_Tools)
        self.pb_Undo.setObjectName(u"pb_Undo")
        self.pb_Undo.setMaximumSize(QSize(60, 16777215))
        self.pb_Undo.setIcon(icon8)
        self.pb_Undo.setIconSize(QSize(25, 25))
        self.pb_Undo.setFlat(True)

        self.verticalLayout.addWidget(self.pb_Undo)

        self.pb_Redo = QPushButton(self.w_Tools)
        self.pb_Redo.setObjectName(u"pb_Redo")
        self.pb_Redo.setMaximumSize(QSize(60, 16777215))
        self.pb_Redo.setIcon(icon9)
        self.pb_Redo.setIconSize(QSize(25, 25))
        self.pb_Redo.setFlat(True)

        self.verticalLayout.addWidget(self.pb_Redo)


        self.horizontalLayout.addWidget(self.w_Tools)

        self.tabWidget = QTabWidget(self.centralwidget)
        self.tabWidget.setObjectName(u"tabWidget")
        self.tabWidget.setMinimumSize(QSize(880, 0))
        self.tabWidget.setMaximumSize(QSize(850, 16777215))
        font = QFont()
        font.setFamilies([u"Yu Gothic UI"])
        font.setBold(True)
        self.tabWidget.setFont(font)
        self.tabWidget.setStyleSheet(u"QTabBar::close-button {\n"
"    image: url(:Tools/close.png);\n"
"	margin-left: 7px;\n"
"	margin-top: 7px;\n"
"	margin-bottom: 5px\n"
"}\n"
"QTabWidget::pane{\n"
"	border: 2px solid;\n"
"	border-radius: 5px;\n"
"}\n"
"\n"
"QTabBar{\n"
"	border-top-right-radius: 10px;\n"
"	border-top-left-radius: 10px;\n"
"	border: 2px solid;\n"
"}\n"
"")
        self.tabWidget.setTabsClosable(True)
        self.tabWidget.setMovable(True)
        self.notebook_1 = QWidget()
        self.notebook_1.setObjectName(u"notebook_1")
        self.notebook_1.setMinimumSize(QSize(850, 0))
        self.notebook_1.setStyleSheet(u"")
        self.gridLayout = QGridLayout(self.notebook_1)
        self.gridLayout.setObjectName(u"gridLayout")
        self.gridLayout.setContentsMargins(0, 0, 0, 0)
        self.scrollArea_2 = QScrollArea(self.notebook_1)
        self.scrollArea_2.setObjectName(u"scrollArea_2")
        self.scrollArea_2.setMinimumSize(QSize(0, 0))
        self.scrollArea_2.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded)
        self.scrollArea_2.setHorizontalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded)
        self.scrollArea_2.setSizeAdjustPolicy(QAbstractScrollArea.SizeAdjustPolicy.AdjustIgnored)
        self.scrollArea_2.setWidgetResizable(True)
        self.scrollAreaWidgetContents_3 = QWidget()
        self.scrollAreaWidgetContents_3.setObjectName(u"scrollAreaWidgetContents_3")
        self.scrollAreaWidgetContents_3.setGeometry(QRect(0, 0, 850, 1100))
        self.scrollAreaWidgetContents_3.setMinimumSize(QSize(850, 1100))
        self.scrollAreaWidgetContents_3.setMaximumSize(QSize(850, 1100))
        self.horizontalLayout_2 = QHBoxLayout(self.scrollAreaWidgetContents_3)
        self.horizontalLayout_2.setObjectName(u"horizontalLayout_2")
        self.horizontalLayout_2.setContentsMargins(0, 0, 0, 0)
        self.gv_Canvas = QGraphicsView(self.scrollAreaWidgetContents_3)
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

        self.scrollArea_2.setWidget(self.scrollAreaWidgetContents_3)

        self.gridLayout.addWidget(self.scrollArea_2, 0, 0, 1, 1)

        self.tabWidget.addTab(self.notebook_1, "")

        self.horizontalLayout.addWidget(self.tabWidget)

        self.horizontalSpacer = QSpacerItem(150, 20, QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Minimum)

        self.horizontalLayout.addItem(self.horizontalSpacer)

        self.pb_AddPage = QPushButton(self.centralwidget)
        self.pb_AddPage.setObjectName(u"pb_AddPage")
        icon12 = QIcon()
        icon12.addFile(u":/Tools/plus.png", QSize(), QIcon.Mode.Normal, QIcon.State.Off)
        self.pb_AddPage.setIcon(icon12)
        self.pb_AddPage.setFlat(True)

        self.horizontalLayout.addWidget(self.pb_AddPage)

        self.horizontalLayout.setStretch(1, 94)
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QMenuBar(MainWindow)
        self.menubar.setObjectName(u"menubar")
        self.menubar.setGeometry(QRect(0, 0, 1060, 33))
        font1 = QFont()
        font1.setBold(True)
        self.menubar.setFont(font1)
        self.menubar.setStyleSheet(u"background-color: rgb(141, 200, 216);\n"
"color: rgb(255, 255, 255);")
        self.menuFile = QMenu(self.menubar)
        self.menuFile.setObjectName(u"menuFile")
        self.menuOptions = QMenu(self.menubar)
        self.menuOptions.setObjectName(u"menuOptions")
        self.menuCollab = QMenu(self.menubar)
        self.menuCollab.setObjectName(u"menuCollab")
        self.menuHelp = QMenu(self.menubar)
        self.menuHelp.setObjectName(u"menuHelp")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QStatusBar(MainWindow)
        self.statusbar.setObjectName(u"statusbar")
        MainWindow.setStatusBar(self.statusbar)
        self.tb_toolbar_main_window = QToolBar(MainWindow)
        self.tb_toolbar_main_window.setObjectName(u"tb_toolbar_main_window")
        self.tb_toolbar_main_window.setEnabled(True)
        self.tb_toolbar_main_window.setMouseTracking(True)
        self.tb_toolbar_main_window.setAutoFillBackground(True)
        MainWindow.addToolBar(Qt.ToolBarArea.TopToolBarArea, self.tb_toolbar_main_window)

        self.menubar.addAction(self.menuFile.menuAction())
        self.menubar.addAction(self.menuOptions.menuAction())
        self.menubar.addAction(self.menuCollab.menuAction())
        self.menubar.addAction(self.menuHelp.menuAction())
        self.menuFile.addAction(self.actionSave_As)
        self.menuFile.addAction(self.actionExport)
        self.menuFile.addAction(self.actionImport)
        self.menuFile.addAction(self.actionClear)
        self.menuFile.addAction(self.actionClose)
        self.menuOptions.addAction(self.actionTemplates)
        self.menuOptions.addAction(self.actionCut)
        self.menuOptions.addAction(self.actionCopy)
        self.menuOptions.addAction(self.actionPaste)
        self.menuOptions.addAction(self.actionSelect_All)
        self.menuOptions.addAction(self.actionDelete)
        self.menuCollab.addAction(self.actionHost)
        self.menuCollab.addAction(self.actionJoin)
        self.menuCollab.addAction(self.actionChat)
        self.menuHelp.addAction(self.actionDocument)
        self.tb_toolbar_main_window.addSeparator()
        self.tb_toolbar_main_window.addAction(self.toolbar_actionCursor)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionPen)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionHighlighter)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionEraser)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionLine)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionShapes)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionImages)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionVideos)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionText)
        self.tb_toolbar_main_window.addAction(self.toolbar_actionSelection)

        self.retranslateUi(MainWindow)

        self.tabWidget.setCurrentIndex(0)


        QMetaObject.connectSlotsByName(MainWindow)
    # setupUi

    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(QCoreApplication.translate("MainWindow", u"MainWindow", None))
        self.actionOpen.setText(QCoreApplication.translate("MainWindow", u"Open", None))
        self.actionSave.setText(QCoreApplication.translate("MainWindow", u"Save", None))
        self.actionSave_As.setText(QCoreApplication.translate("MainWindow", u"Save As", None))
        self.actionClear.setText(QCoreApplication.translate("MainWindow", u"Clear", None))
        self.actionClose.setText(QCoreApplication.translate("MainWindow", u"Exit", None))
        self.actionTemplates.setText(QCoreApplication.translate("MainWindow", u"Templates", None))
        self.actionShare.setText(QCoreApplication.translate("MainWindow", u"Share", None))
        self.actionExport.setText(QCoreApplication.translate("MainWindow", u"Export", None))
        self.actionImport.setText(QCoreApplication.translate("MainWindow", u"Import", None))
        self.actionDocument.setText(QCoreApplication.translate("MainWindow", u"Document", None))
        self.actionHost.setText(QCoreApplication.translate("MainWindow", u"Host", None))
        self.actionJoin.setText(QCoreApplication.translate("MainWindow", u"Join", None))
        self.actionCut.setText(QCoreApplication.translate("MainWindow", u"Cut", None))
        self.actionCopy.setText(QCoreApplication.translate("MainWindow", u"Copy", None))
        self.actionPaste.setText(QCoreApplication.translate("MainWindow", u"Paste", None))
        self.actionSelect_All.setText(QCoreApplication.translate("MainWindow", u"Select All", None))
        self.actionDelete.setText(QCoreApplication.translate("MainWindow", u"Delete", None))
        self.actionUndo.setText(QCoreApplication.translate("MainWindow", u"Undo", None))
        self.actionRedo.setText(QCoreApplication.translate("MainWindow", u"Redo", None))
        self.toolbar_actionCursor.setText(QCoreApplication.translate("MainWindow", u"Cursor", None))
        self.toolbar_actionPen.setText(QCoreApplication.translate("MainWindow", u"Pen", None))
        self.toolbar_actionHighlighter.setText(QCoreApplication.translate("MainWindow", u"Highlighter", None))
        self.toolbar_actionEraser.setText(QCoreApplication.translate("MainWindow", u"Eraser", None))
        self.toolbar_actionLine.setText(QCoreApplication.translate("MainWindow", u"Line", None))
        self.toolbar_actionShapes.setText(QCoreApplication.translate("MainWindow", u"Shapes", None))
        self.toolbar_actionImages.setText(QCoreApplication.translate("MainWindow", u"Images", None))
        self.toolbar_actionVideos.setText(QCoreApplication.translate("MainWindow", u"Videos", None))
        self.actionUndo_2.setText(QCoreApplication.translate("MainWindow", u"Undo", None))
        self.actionRedo_2.setText(QCoreApplication.translate("MainWindow", u"Redo", None))
        self.toolbar_actionSelection.setText(QCoreApplication.translate("MainWindow", u"Selection", None))
        self.toolbar_actionText.setText(QCoreApplication.translate("MainWindow", u"Text", None))
        self.actionChat.setText(QCoreApplication.translate("MainWindow", u"Chat", None))
        self.actionNew.setText(QCoreApplication.translate("MainWindow", u"New", None))
        self.pb_Cursor.setText("")
        self.tb_Pen.setText("")
        self.tb_Highlighter.setText("")
        self.tb_Line.setText("")
        self.tb_Select.setText("")
        self.pb_Eraser.setText("")
        self.pb_Text_Button.setText("")
        self.tb_Text.setText("")
        self.tb_Shapes.setText("")
        self.tb_Images.setText("")
        self.tb_Videos.setText("")
        self.pb_Undo.setText("")
        self.pb_Redo.setText("")
        self.tabWidget.setTabText(self.tabWidget.indexOf(self.notebook_1), QCoreApplication.translate("MainWindow", u"Notebook 1", None))
        self.pb_AddPage.setText("")
        self.menuFile.setTitle(QCoreApplication.translate("MainWindow", u"File", None))
        self.menuOptions.setTitle(QCoreApplication.translate("MainWindow", u"Edit", None))
        self.menuCollab.setTitle(QCoreApplication.translate("MainWindow", u"Collab", None))
        self.menuHelp.setTitle(QCoreApplication.translate("MainWindow", u"Help", None))
        self.tb_toolbar_main_window.setWindowTitle(QCoreApplication.translate("MainWindow", u"toolBar", None))
    # retranslateUi

