from PySide6.QtWidgets import (
    QMainWindow,
    QGraphicsScene,
    QApplication,
    QGraphicsPathItem,
    QColorDialog,
    QPushButton,
    QGraphicsTextItem
)

from PySide6.QtGui import (
    QPen,
    Qt,
    QPainter,
    QPainterPath,
    QColor,
    QTransform
)

from PySide6.QtCore import (
    Qt
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

        #A list to keep track of all existing text boxes
        self.text_boxes = []

    def change_color(self, color):
        self.color = color

    def change_size(self, size):
        self.size = size

    def mousePressEvent(self, event):
        if event.button() == Qt.MouseButton.LeftButton:
        # Check if clicked on an existing text box
            item = self.itemAt(event.scenePos(), QTransform())
            if isinstance(item, QGraphicsTextItem):
                self.selected_textbox = item
                self.moving_textbox = True  # Flag to indicate movement
            else:
                self.selected_textbox = None
                self.moving_textbox = False  # No movement if not on a text box
        super().mousePressEvent(event)

    def mouseMoveEvent(self, event):
        if self.moving_textbox and self.selected_textbox:
            # Move the selected text box
            self.selected_textbox.setPos(event.scenePos())
        else:
            if self.drawing:
                curr_position = event.scenePos()
                self.path.lineTo(curr_position)
                self.pathItem.setPath(self.path)
                self.previous_position = curr_position
        super().mouseMoveEvent(event)


    def mouseReleaseEvent(self, event):
        # Release movement
        if self.moving_textbox:
            self.moving_textbox = False
            self.selected_textbox = None
        super().mouseReleaseEvent(event)

    def add_text_box(self):
        text_box = TextBox()
        self.addItem(text_box)
        # Set the position to a visible area (e.g., center of the scene)
        scene_rect = self.sceneRect()
        text_box.setPos(scene_rect.width() / 2 - text_box.boundingRect().width() / 2,
                    scene_rect.height() / 2 - text_box.boundingRect().height() / 2)

        self.text_boxes.append(text_box)

    def remove_text_box(self, text_box):
        if text_box in self.text_boxes:
            self.text_boxes.remove(text_box)  # Remove from the list
        self.removeItem(text_box)  # Remove from the scene

class MainWindow(QMainWindow, Ui_MainWindow):
    def __init__(self):
        super().__init__()
        self.setupUi(self)

        # Add pb_BackgroundColor button - Chloe
        self.pb_BackgroundColor = QPushButton("Change Background Color", self)
        self.pb_BackgroundColor.setGeometry(10, 195, 150, 30)
        self.pb_BackgroundColor.clicked.connect(self.change_background_color)

        self.pb_Text_Box = QPushButton("Text Box", self)
        self.pb_Text_Box.setGeometry(10, 400, 100, 30)
        self.pb_Text_Box.clicked.connect(self.add_text_box)

        ############################################################################################################
        # Ensure all buttons behave properly when clicked
        self.list_of_buttons = [self.pb_Pen, self.pb_Eraser]

        self.pb_Pen.setChecked(True)
        self.pb_Pen.clicked.connect(self.button_clicked)
        self.pb_Eraser.clicked.connect(self.button_clicked)

        self.current_color = QColor("#000000")

        ############################################################################################################

        self.actionClear.triggered.connect(self.clear_canvas)

        # Define what the tool buttons do
        ###########################################################################################################
        self.current_color = QColor("#000000")
        self.pb_Pen.clicked.connect(lambda e: self.color_changed(self.current_color))
        # This eraser just changes stuff to white (#FFFFFF) and not to the proper background color of window... - RS 10/30
        # I used an online tool to find out the proper color of the background and updated it below... - RS 10/30
        self.pb_Eraser.clicked.connect(lambda e: self.color_changed(QColor("#F3F3F3")))

        self.dial.sliderMoved.connect(self.change_size)
        self.dial.setMinimum(1)
        self.dial.setMaximum(100)
        self.dial.setWrapping(False)

        self.pb_Color.clicked.connect(self.color_dialog)
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
            self.pb_Pen.setChecked(True)

    def color_changed(self, color):
        self.scene.change_color(color)

    def button_clicked(self):
        sender_button = self.sender()
        for btn in self.list_of_buttons:
            if btn is not sender_button:
                btn.setChecked(False)

    def change_background_color(self):
        # Open a color board and set the background color
        color = QColorDialog.getColor()
        if color.isValid():
            # Update backround color
            self.scene.setBackgroundBrush(color)

    def add_text_box(self):
        self.scene.add_text_box()


if __name__ == '__main__':
    app = QApplication()

    window = MainWindow()
    window.show()

    app.exec()
