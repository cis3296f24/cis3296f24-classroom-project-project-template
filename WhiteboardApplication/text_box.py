from PySide6.QtWidgets import (
    QGraphicsTextItem, QGraphicsRectItem, QGraphicsItem, QColorDialog, QFontDialog, QMenu
)
from PySide6.QtGui import QFont, QPen, QColor, QKeySequence
from PySide6.QtCore import Qt, QRectF, QSizeF, QPointF
from resize_handles import ResizeHandle

class TextBox(QGraphicsTextItem):
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

        # Create background rectangle
        self.background = QGraphicsRectItem(self)
        self.background.setBrush(QColor(255, 255, 255, 127))
        self.background.setPen(QPen(Qt.black))
        self.background.setFlag(QGraphicsItem.ItemStacksBehindParent)

        # Create resize handles
        self.handles = {}
        for position in ['topleft', 'top', 'topright', 'right','bottomright', 'bottom', 'bottomleft', 'left']:
            self.handles[position] = ResizeHandle(position, self)

        # Set initial size
        #self.setMinimumWidth(100)
        self.resize(200, 100)

        # Track initial position for dragging calculations
        self.start_pos = QPointF()

        # Update handle positions
        self.updateHandlePositions()

    def resize(self, width, height):
        # Update the text document size
        self.setTextWidth(width)

        # Update the background rectangle
        self.background.setRect(0, 0, width, height)

        # Update handle positions
        self.updateHandlePositions()

    def updateHandlePositions(self):
        rect = self.background.rect()
        handle_size = self.handles['topleft'].rect().width()

        # Position mapping for each handle
        positions = {
            'topleft': (0, 0),
            'top': (rect.width()/2 - handle_size/2, 0),
            'topright': (rect.width() - handle_size, 0),
            'right': (rect.width() - handle_size, rect.height()/2 - handle_size/2),
            'bottomright': (rect.width() - handle_size, rect.height() - handle_size),
            'bottom': (rect.width()/2 - handle_size/2, rect.height() - handle_size),
            'bottomleft': (0, rect.height() - handle_size),
            'left': (0, rect.height()/2 - handle_size/2)
        }

        # Update each handle position
        for position, (x, y) in positions.items():
            self.handles[position].setPos(x, y)

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            # Set focus to the text box when left-clicked
            self.setFocus()

            # Store the starting position for dragging relative to the box
            self.start_pos = event.scenePos() - self.pos()  # Calculate offset
            print("Hit left button once")
            super().mousePressEvent(event)
        elif event.button() == Qt.RightButton:
            # Handle right-click to show context menu
            if self.isSelected():
                self.contextMenuEvent(event)
        else:
            super().mousePressEvent(event)

    def mouseMoveEvent(self, event):
        if event.buttons() == Qt.LeftButton and not self.textCursor().hasSelection():
            # Move the text box based on the current mouse position minus the start position
            self.setPos(event.scenePos() - self.start_pos)  # Use the offset for movement
            event.accept()
        else:
            super().mouseMoveEvent(event)

    def mouseReleaseEvent(self, event):
        super().mouseReleaseEvent(event)

    def boundingRect(self):
        return self.background.rect()

    def shape(self):
        return self.background.shape()

    def contextMenuEvent(self, event):
        super().contextMenuEvent(event)

    def keyPressEvent(self, event):
        if event.key() == Qt.Key_Backspace:
            # Regular Backspace behavior for text deletion
            if self.textInteractionFlags() == Qt.TextEditorInteraction:
                super().keyPressEvent(event)
                self.updateHandlePositions()

        else:
            # Let other keys work as usual
            super().keyPressEvent(event)

    def contextMenuEvent(self, event):
        # Create a context menu
        context_menu = QMenu()
        change_color_action = context_menu.addAction("Change Color")
        change_text_action = context_menu.addAction("Change Text")
        bold_action = context_menu.addAction("Bold")
        italic_action = context_menu.addAction("Italic")
        underline_action = context_menu.addAction("Underline")
        delete_action = context_menu.addAction("Delete Box")  # New delete action


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

        elif action == delete_action:
            self.delete()

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

    def delete(self):
        scene = self.scene()
        if scene:
            scene.removeItem(self)  # Remove the item from the scene
        self.deleteLater()  # Schedule the item for deletion