from PySide6.QtWidgets import QGraphicsRectItem, QGraphicsItem
from PySide6.QtGui import QPen, QColor
from PySide6.QtCore import Qt, QRectF


class ResizeHandle(QGraphicsRectItem):
    def __init__(self, position, color=QColor(255, 255, 255), parent=None):
        super().__init__(0, 0, 8, 8, parent)
        self.position = position
        self.setAcceptHoverEvents(True)
        self.setPen(QPen(QColor(0, 0, 0)))
        self.default_color = color  # Store the original color
        self.setBrush(self.default_color)  # Set the initial color
        self.setFlag(QGraphicsItem.ItemIsMovable, True)
        self.setFlag(QGraphicsItem.ItemSendsGeometryChanges, True)

        # Sets the cursor type based on positions, with diagonals for the corners
        # vertical for the top and bottom, and horizontal for the sides
        cursors = {
            'topleft': Qt.SizeFDiagCursor,
            'top': Qt.SizeVerCursor,
            'topright': Qt.SizeBDiagCursor,
            'right': Qt.SizeHorCursor,
            'bottomright': Qt.SizeFDiagCursor,
            'bottom': Qt.SizeVerCursor,
            'bottomleft': Qt.SizeBDiagCursor,
            'left': Qt.SizeHorCursor
        }
        self.setCursor(cursors[position])

    #Checks if the resizing handles are being interacted with and highlights them
    def hoverEnterEvent(self, event):
        self.setBrush(QColor(200, 200, 200))  # Light gray on hover
        super().hoverEnterEvent(event)

    def hoverLeaveEvent(self, event):
        # Reset the color when mouse leaves the handle
        self.setBrush(self.default_color)  # Restore the original color
        event.accept()

    #Functions to hide and show resizing handles when the box is clicked
    def hideHandle(self):
        self.setVisible(False)

    def showHandle(self):
        self.setVisible(True)


    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            event.accept()
        else:
            super().mousePressEvent(event)


    def mouseReleaseEvent(self, event):
        # Notify TextBox to re-enable drawing
        text_box = self.parentItem()
        if text_box:
            text_box.drawing_disabled = False
        super().mouseReleaseEvent(event)

    def mouseMoveEvent(self, event):
        if event.buttons() == Qt.LeftButton:
            text_box = self.parentItem()
            if text_box:
                pos = event.pos()
                scene_pos = event.scenePos()
                text_rect = text_box.background.rect()
                text_box_pos = text_box.scenePos()

                # Calculate new dimensions based on handle position
                new_rect = QRectF(text_rect)

                # Handle left-side resizing
                if self.position in ['topleft', 'left', 'bottomleft']:
                    delta_x = scene_pos.x() - text_box_pos.x()
                    new_width = text_rect.right() - delta_x
                    if new_width >= 50:  # Minimum width
                        new_rect.setLeft(delta_x)
                        # Only move the box if resizing from the left side
                        text_box.setPos(text_box_pos.x() + delta_x, text_box_pos.y())

                # Handle right-side resizing
                if self.position in ['topright', 'bottomright', 'right']:
                    delta_x = scene_pos.x() - text_box_pos.x()
                    new_rect.setRight(delta_x)

                # Handle top-side resizing
                if self.position in ['topleft', 'top', 'topright']:
                    delta_y = scene_pos.y() - text_box_pos.y()
                    new_height = text_rect.bottom() - delta_y
                    if new_height >= 30:  # Minimum height
                        new_rect.setTop(delta_y)
                        # Only move the box if resizing from the top side
                        text_box.setPos(text_box_pos.x(), text_box_pos.y() + delta_y)

                # Handle bottom-side resizing
                if self.position in ['bottomleft', 'bottom', 'bottomright']:
                    delta_y = scene_pos.y() - text_box_pos.y()
                    new_rect.setBottom(delta_y)

                # Apply minimum size constraints
                if new_rect.width() >= 50 and new_rect.height() >= 30:
                    text_box.resize(new_rect.width(), new_rect.height())

                    # Update the position of the text box if it's being resized from the left or top
                    if self.position in ['topleft', 'left', 'bottomleft']:
                        text_box.setPos(text_box_pos.x() + (text_rect.width() - new_rect.width()), text_box_pos.y())
                    if self.position in ['topleft', 'top', 'topright']:
                        text_box.setPos(text_box_pos.x(), text_box_pos.y() + (text_rect.height() - new_rect.height()))

                event.accept()
        else:
            super().mouseMoveEvent(event)
