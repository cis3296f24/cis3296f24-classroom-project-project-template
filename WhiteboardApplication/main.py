from PySide6.QtWidgets import (
    QMainWindow,
    QGraphicsScene,
    QApplication,
    QGraphicsPathItem,
    QColorDialog,
    QPushButton,
    QGraphicsTextItem, QToolBar
)

from PySide6.QtGui import (
    QPen,
    Qt,
    QPainter,
    QPainterPath,
    QColor,
    QTransform, QAction
)

from PySide6.QtCore import (
    Qt, QRectF, QSizeF, QPointF
)

from WhiteboardApplication.UI.board import Ui_MainWindow

from text_box import TextBox

class BoardScene(QGraphicsScene):
    def __init__(self):
        super().__init__()

        self.setSceneRect(0, 0, 600, 500)

        self.path = None
        self.previous_position = None
        self.drawing = False
        self.color = QColor("#000000")
        self.size = 1
        self.pathItem = None

        #Added flags to check which button is being pressed, and if text boxes are being dragged
        self.is_text_box_selected = False
        self.dragging_text_box = False
        self.drawing_enabled = False
        self.erasing_enabled = False
        self.active_tool = None



    def change_color(self, color):
        self.color = color

    def change_size(self, size):
        self.size = size

    #Used to turn drawing on or off so it doesn't interfere with dragging text boxes
    def enable_drawing(self, enable):
        self.drawing_enabled = enable
        # Ensure eraser is off when drawing is on
        if enable:
            self.erasing_enabled = False

    #Used to turn eraser on or off so it doesn't interfere with dragging text boxes
    def enable_eraser(self, enable):
        self.erasing_enabled = enable
        # Ensure drawing is off when erasing is on
        if enable:
            self.drawing_enabled = False

    def mousePressEvent(self, event):
        item = self.itemAt(event.scenePos(), QTransform())

        print(f"Active Tool: {self.active_tool}")  # Debugging print

        # Check if the left button is clicked
        if event.button() == Qt.LeftButton:
            # If a text box is clicked, start dragging
            if isinstance(item, TextBox):
                print("Box selected")
                self.drawing = False
                self.is_text_box_selected = True
                self.selected_text_box = item
                self.start_pos = event.scenePos()  # Store the start position for dragging
                self.dragging_text_box = True  # Set dragging flag
            else:
                # Check active tool and proceed accordingly
                if self.active_tool == "pen":
                    print("Pen tool active")
                    self.drawing = True
                    self.path = QPainterPath()
                    self.previous_position = event.scenePos()
                    self.path.moveTo(self.previous_position)
                    self.pathItem = QGraphicsPathItem()
                    my_pen = QPen(self.color, self.size)
                    my_pen.setCapStyle(Qt.PenCapStyle.RoundCap)
                    self.pathItem.setPen(my_pen)
                    self.addItem(self.pathItem)
                elif self.active_tool == "eraser":
                    print("Eraser tool active")
                    self.drawing = False  # Ensure we're not drawing
                    self.erase(event.scenePos())  # Start erasing


        super().mousePressEvent(event)

    #A basic eraser, created as a hold so text box function could be implemented
    def erase(self, position):
        eraser_radius = 10

        #Creates a 20 x 20 rectangle, using the current position and moving further left and up to set the left corner of the rectangle
        erase_item = self.items(QRectF(position - QPointF(eraser_radius, eraser_radius), QSizeF(eraser_radius * 2, eraser_radius * 2)))

        #Removes all items within the rectangle
        for item in erase_item:
            if isinstance(item, QGraphicsPathItem):
                self.removeItem(item)

    def mouseMoveEvent(self, event):
        # Handle dragging the text box if it's selected
        if self.dragging_text_box and self.selected_text_box:
            self.drawing = False
            print("Dragging box")
            delta = event.scenePos() - self.start_pos
            self.selected_text_box.setPos(self.selected_text_box.pos() + delta)
            self.start_pos = event.scenePos()  # Update start position for the next move

        elif self.drawing:
            print("drawing")
            # Continue drawing if no text box is selected
            curr_position = event.scenePos()
            self.path.lineTo(curr_position)
            self.pathItem.setPath(self.path)
            self.previous_position = curr_position

        super().mouseMoveEvent(event)

    def mouseReleaseEvent(self, event):
        if event.button() == Qt.LeftButton:
            if self.dragging_text_box:
                print("Finished dragging box")
                self.dragging_text_box = False
        self.drawing = False  # Reset drawing state
        self.is_text_box_selected = False  # Reset selection when mouse is released

        super().mouseReleaseEvent(event)

    #Adds a text box when button is clicked
    def add_text_box(self):
        text_box = TextBox()
        self.addItem(text_box)
        # Set the position to a visible area (e.g., center of the scene)
        scene_rect = self.sceneRect()
        text_box.setPos(scene_rect.width() / 2 - text_box.boundingRect().width() / 2,
                        scene_rect.height() / 2 - text_box.boundingRect().height() / 2)

    #Marks which tool (pen, eraser) is being used so multiple don't run at once
    def set_active_tool(self, tool):
        self.active_tool = tool

class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        # # Add pb_BackgroundColor button - Chloe
        # self.pb_BackgroundColor = QPushButton("Change Background Color", self)
        # self.pb_BackgroundColor.setGeometry(10, 195, 150, 30)
        # self.pb_BackgroundColor.clicked.connect(self.change_background_color)

        ############################################################################################################
        # Ensure all buttons behave properly when clicked
        self.list_of_buttons = [self.tb_Pen, self.pb_Eraser]

        self.tb_Pen.setChecked(True)
        self.tb_Pen.clicked.connect(self.button_clicked)
        self.pb_Eraser.clicked.connect(self.button_clicked)
        self.pb_Text_Button.clicked.connect(self.add_text_box)

        #sharron helped me out by showing this below
        self.toolbar_actionText.triggered.connect(self.add_text_box)
        #self.toolbar_actionLine.triggered.connect(self.tb_Line)
        self.toolbar_actionEraser.setChecked(True)
        self.toolbar_actionEraser.triggered.connect(self.button_clicked)


        self.current_color = QColor("#000000")

        ############################################################################################################

        self.actionClear.triggered.connect(self.clear_canvas)

        # Define what the tool buttons do
        ###########################################################################################################
        self.current_color = QColor("#000000")
        #self.tb_Pen.triggered.connect(lambda e: self.color_changed(self.current_color))
        # This eraser just changes stuff to white (#FFFFFF) and not to the proper background color of window... - RS 10/30
        # I used an online tool to find out the proper color of the background and updated it below... - RS 10/30
        #self.pb_Eraser.clicked.connect(lambda e: self.color_changed(QColor("#F3F3F3")))

        self.dial.sliderMoved.connect(self.change_size)
        self.dial.setMinimum(1)
        self.dial.setMaximum(100)
        self.dial.setWrapping(False)

        #self.pb_Color.clicked.connect(self.color_dialog)
        self.pb_Undo.clicked.connect(self.undo)
        self.pb_Redo.clicked.connect(self.redo)
        ###########################################################################################################

        self.scene = BoardScene()
        self.gv_Canvas.setScene(self.scene)
        self.gv_Canvas.setRenderHint(QPainter.RenderHint.Antialiasing, True)

        self.redo_list = []

    def change_size(self):
        self.scene.change_size(self.dial.value())

    def undo(self):
        if self.scene.items():
            latest_item = self.scene.items()
            self.redo_list.append(latest_item)
            self.scene.removeItem(latest_item[0])

    def redo(self):
        if self.redo_list:
            item = self.redo_list.pop(-1)
            self.scene.addItem(item[0])

    def clear_canvas(self):
        self.scene.clear()

    def color_dialog(self):
        color_dialog = QColorDialog()
        color_dialog.show()
        color_dialog.currentColorChanged.connect(lambda e: self.color_dialog_color_changed(color_dialog.currentColor()))
        self.current_color = color_dialog.currentColor()

    def color_dialog_color_changed(self, current_color):
        self.color_changed(current_color)
        if self.pb_Eraser.isChecked():
            self.pb_Eraser.setChecked(False)
            self.tb_Pen.setChecked(True)

    def color_changed(self, color):
        self.scene.change_color(color)

    #Depending on which button is clicked, sets the appropriate flag so that operations
    #don't overlap
    def button_clicked(self):
        sender_button = self.sender()

        # Toggle Pen
        if sender_button == self.tb_Pen:
            if self.tb_Pen.isChecked():
                # Enable pen mode, disable eraser
                print("Pen activated")  # Debugging print
                self.color_changed(self.current_color)
                self.scene.set_active_tool("pen")
                self.pb_Eraser.setChecked(False)  # Ensure eraser is not active
            else:
                # Deactivate drawing mode when button is clicked again
                print("Pen deactivated")  # Debugging print
                self.scene.set_active_tool(None)

        # Toggle Eraser
        elif sender_button == self.pb_Eraser:
            if self.pb_Eraser.isChecked():
                # Enable eraser mode, disable pen
                print("Eraser activated")  # Debugging print
                self.color_changed(QColor("#F3F3F3"))
                self.scene.set_active_tool("eraser")
                self.tb_Pen.setChecked(False)  # Ensure pen is not active
            else:
                # Deactivate erasing mode when button is clicked again
                print("Eraser deactivated")  # Debugging print
                self.scene.set_active_tool(None)

        elif sender_button == self.toolbar_actionEraser:
            if self.toolbar_actionEraser.isChecked():
                print("Eraser activated")
                self.color_changed(QColor("#F3F3F3"))
                self.scene.set_active_tool("eraser")
                self.tb_Pen.setChecked(False)
            else:
                print("Eraser deactivated")
                self.scene.set_active_tool(None)

    #Adds a text box using the method in BoardScene
    def add_text_box(self):
        self.scene.add_text_box()

    # def change_background_color(self):
    #     # Open a color board and set the background color
    #     color = QColorDialog.getColor()
    #     if color.isValid():
    #         # Update backround color
    #         self.scene.setBackgroundBrush(color)

    def enable_eraser(self, enable):
        self.erasing_enabled = enable
        # Ensure drawing is off when erasing is on
        if enable:
            self.drawing_enabled = False


if __name__ == '__main__':
    app = QApplication()

    window = MainWindow()
    window.show()

    app.exec()