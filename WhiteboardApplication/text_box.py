from PySide6.QtWidgets import (QGraphicsTextItem, QGraphicsRectItem, QGraphicsItem)
from PySide6.QtGui import QKeyEvent
from PySide6.QtGui import QFont, QPainter
from PySide6.QtCore import Qt, QRectF, QSizeF, QPointF



class TextBox (QGraphicsTextItem):
    def __init__(self):
        super().__init__()

        # Set default text, font, and color
        self.setPlainText("Type Here")
        self.setFont(QFont("Arial", 12))
        self.setDefaultTextColor(Qt.black)

        # Enable the text box to be moved, selected, and focused
        self.setFlags(QGraphicsTextItem.ItemIsMovable |
                      QGraphicsTextItem.ItemIsSelectable |
                      QGraphicsTextItem.ItemIsFocusable)

        # Allow text editing within the box
        self.setTextInteractionFlags(Qt.TextEditorInteraction)

        # Create a resize handle to adjust text box size
        self.resize_handle = QGraphicsRectItem(0, 0, 10, 10, self)
        self.resize_handle.setBrush(Qt.black)
        #self.resize_handle.setFlags(QGraphicsItem.ItemIsMovable)
        self.resize_handle.setCursor(Qt.SizeFDiagCursor)

        # Track initial position for dragging calculations
        self.start_pos = QPointF()

        # Position the resize handle correctly initially
        self.updateResizeHandlePosition()

    def updateResizeHandlePosition(self):
        # Update the position of the resize handle based on the text box's dimensions
        rect = self.boundingRect()
        self.resize_handle.setPos(self.boundingRect().width() - 10, self.boundingRect().height() - 10)

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            # Record the initial scene position when clicked
            self.start_pos = event.scenePos()
            self.setFocus()
            event.accept()
        super().mousePressEvent(event)

    #Allows the text box to be dragged around the screen with the mouse and for resizing depending
    #on where the mouse is
    def mouseMoveEvent(self, event):
        if event.buttons() == Qt.LeftButton:
            offset = event.scenePos() - self.start_pos
            self.setPos(self.pos() + offset)
            self.start_pos = event.scenePos()  # Update start_pos for continued movement
            event.accept()
        else:
            super().mouseMoveEvent(event)

    #Allows the text box to be placed anywhere on the screen when the mouse is released,
    #or ends resizing
    def mouseReleaseEvent(self, event):
        super().mouseReleaseEvent(event)

    #Deletes text box
    def keyPressEvent(self, event):
        if event.key() == Qt.Key_Backspace:
            scene = self.scene()
            if scene:
                scene.removeItem(self)
        else:
            super().keyPressEvent(event)
            self.updateResizeHandlePosition()  # Update the handle position after typing

