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
        if event.key() == Qt.Key_Backspace:
            scene = self.scene()
            if scene:
                scene.removeItem(self)
        else:
            super().keyPressEvent(event)
            self.updateResizeHandlePosition()  # Update the handle position after typing

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
            new_color = QColorDialog.getColor()
            if new_color.isValid():
                cursor = self.textCursor()
                fmt = cursor.charFormat()
                fmt.setForeground(new_color)  # Set foreground color to the new color
                cursor.mergeCharFormat(fmt)  # Apply the format to the selected text

        elif action == change_text_action:
            ok, font = QFontDialog.getFont()  # Show the font dialog
            if ok:
                self.setFont(font)# User clicked OK

        elif action == bold_action:
            cursor = self.textCursor()
            fmt = cursor.charFormat()
            fmt.setFontWeight(QFont.Bold if fmt.fontWeight() != QFont.Bold else QFont.Normal)
            cursor.mergeCharFormat(fmt)

        elif action == italic_action:
            cursor = self.textCursor()
            fmt = cursor.charFormat()
            fmt.setFontItalic(not fmt.fontItalic())
            cursor.mergeCharFormat(fmt)

        elif action == underline_action:
            cursor = self.textCursor()
            fmt = cursor.charFormat()
            fmt.setFontUnderline(not fmt.fontUnderline())
            cursor.mergeCharFormat(fmt)

    def change_font(self):
        cursor = self.text_edit.textCursor()
        if cursor.hasSelection():  # Check if text is selected
            print("Text is selected.")
            font, ok = QFontDialog.getFont()
            if ok:  # Only apply if the user clicked OK
                fmt = cursor.charFormat()
                fmt.setFont(font)  # Set the new font
                cursor.mergeCharFormat(fmt)  # Apply the format to the selected text
        else:
            print("No text selected.")
        '''
        if font.isValid():  # Only apply if the user clicked OK
            cursor = self.textCursor()
            fmt = cursor.charFormat()
            fmt.setFont(font)  # Set the new font
            cursor.mergeCharFormat(fmt)  # Apply the format to the selected text
        '''

    def change_color(self):
        # Open a color dialog
        color = QColorDialog.getColor(self.defaultTextColor(), self.parentItem())
        if color.isValid():
            self.setDefaultTextColor(color)

    def change_font_size(self):
        # Open a simple dialog to input font size
        size, ok = QInputDialog.getInt(self, "Font Size", "Enter new font size:", self.font().pointSize(), 1, 100)
        if ok:
            current_font = self.font()
            current_font.setPointSize(size)
            self.setFont(current_font)

    def toggle_bold(self):
        current_font = self.font()
        current_font.setBold(not current_font.bold())
        self.setFont(current_font)

    def toggle_italic(self):
        current_font = self.font()
        current_font.setItalic(not current_font.italic())
        self.setFont(current_font)

    def toggle_underline(self):
        current_font = self.font()
        current_font.setUnderline(not current_font.underline())
        self.setFont(current_font)

