from PySide6.QtWidgets import (
    QGraphicsTextItem, QGraphicsRectItem, QGraphicsItem, QMenu, QFontDialog,
    QColorDialog, QInputDialog)
from PySide6.QtGui import QFont, QPainter, QTextCharFormat
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
            # Set focus to the text box when left-clicked
            self.setFocus()
            super().mousePressEvent(event)
        elif event.button() == Qt.RightButton:
            # Handle right-click to show context menu
            if self.isSelected():  # Show context menu only if the text is selected
                self.contextMenuEvent(event)
            else:
                super().mousePressEvent(event)  # Default behavior for moving
        else:
            super().mousePressEvent(event)

    #Allows the text box to be dragged around the screen with the mouse and for resizing depending
    #on where the mouse is
    def mouseMoveEvent(self, event):
        # Prevent moving the text box while selecting text
        if event.buttons() == Qt.LeftButton and not self.textCursor().hasSelection():
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
        if self.hasFocus and event.key() == Qt.Key_Backspace:
            if not self.textCursor().hasSelection():
                scene = self.scene()
                if scene:
                    scene.removeItem(self)
            else:
                super().keyPressEvent(event)
                self.updateResizeHandlePosition()  # Update the handle position after typing
        else:
            super().keyPressEvent(event)

    def contextMenuEvent(self, event):
        # Create a context menu
        context_menu = QMenu()
        change_color_action = context_menu.addAction("Change Color")
        change_text_action = context_menu.addAction("Change Text")
        bold_action = context_menu.addAction("Bold")
        italic_action = context_menu.addAction("Italic")
        underline_action = context_menu.addAction("Underline")

        # Execute the menu and capture the action
        action = context_menu.exec_(event.screenPos())

        if action == change_color_action:
            self.change_color()

        elif action == change_text_action:
            self.change_font()

        elif action == bold_action:
            self.bold()

        elif action == italic_action:
            self.italic()

        elif action == underline_action:
            self.underline()



    def change_color(self):
        new_color = QColorDialog.getColor()
        if new_color.isValid():
            cursor = self.textCursor()
            fmt = cursor.charFormat()
            fmt.setForeground(new_color)  # Set foreground color to the new color
            cursor.mergeCharFormat(fmt)  # Apply the format to the selected text

    def change_font(self):
        ok, font = QFontDialog.getFont()  # Show the font dialog
        if ok:
            self.setFont(font)# User clicked OK

    def bold(self):
        cursor = self.textCursor()
        fmt = cursor.charFormat()
        fmt.setFontWeight(QFont.Bold if fmt.fontWeight() != QFont.Bold else QFont.Normal)
        cursor.mergeCharFormat(fmt)

    def italic(self):
        cursor = self.textCursor()
        fmt = cursor.charFormat()
        fmt.setFontItalic(not fmt.fontItalic())
        cursor.mergeCharFormat(fmt)

    def underline(self):
        cursor = self.textCursor()
        fmt = cursor.charFormat()
        fmt.setFontUnderline(not fmt.fontUnderline())
        cursor.mergeCharFormat(fmt)

