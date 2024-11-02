from PySide6.QtWidgets import (QGraphicsTextItem, QGraphicsRectItem, QGraphicsItem)
from PySide6.QtGui import QKeyEvent
from PySide6.QtGui import QFont, QPainter
from PySide6.QtCore import Qt, QRectF



class TextBox (QGraphicsTextItem):
    def __init__(self):
        super().__init__()
        self.setPlainText("Type Here")
        self.setFont(QFont("Arial", 12))
        self.setDefaultTextColor(Qt.black)  # Set text color to black


        #Defines properties of a text box, including that it can be moved, selected, and recieve user input
        self.setFlags(QGraphicsTextItem.ItemIsMovable |
                      QGraphicsTextItem.ItemIsSelectable |
                      QGraphicsTextItem.ItemIsFocusable)
        self.setTextInteractionFlags(Qt.TextEditorInteraction)

        #Creates handle that allows textbox to be resized
        self.resize_handle = QGraphicsRectItem(0,0,10,10,self)
        self.resize_handle.setBrush(Qt.black)
        self.resize_handle.setFlags(QGraphicsItem.ItemIsMovable)

        #Changes cursor to a diagonal when resizing
        self.resize_handle.setCursor(Qt.SizeFDiagCursor)

        self.is_resizing = False

        self.updateResizeHandlePosition()

    def updateResizeHandlePosition(self):
        # Update the position of the resize handle based on the text box's dimensions
        self.resize_handle.setPos(self.boundingRect().width() - 10, self.boundingRect().height() - 10)

    def mousePressEvent(self, event):
        #Checks if resize handle is being clicked, else it just allows for the box to be dragged
        if event.button() == Qt.LeftButton and self.resize_handle.isUnderMouse():
            self.is_resizing = True

        super().mousePressEvent(event)

    #Allows the text box to be dragged around the screen with the mouse and for resizing depending
    #on where the mouse is
    def mouseMoveEvent(self, event):
        if self.is_resizing:
            new_rect = QRectF(self.pos(), event.scenePos() - self.pos())
            self.setRect(new_rect)  # Assuming setRect is defined to adjust the text box's size
            self.updateResizeHandlePosition()  # Update the position of the resize handle
        else:
            super().mouseMoveEvent(event)

    #Allows the text box to be placed anywhere on the screen when the mouse is released,
    #or ends resizing
    def mouseReleaseEvent(self, event):
        self.is_resizing = False

    #Deletes text box
    def keyPressEvent(self, event):
        if event.key() == Qt.Key_Delete:
            scene = self.scene()
            if scene:
                scene.removeItem(self)
        else:
            super().keyPressEvent(event)


