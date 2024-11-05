from PySide6.QtWidgets import (
    QGraphicsTextItem, QGraphicsRectItem, QGraphicsItem
)
from PySide6.QtGui import QFont, QPen, QColor
from PySide6.QtCore import Qt, QRectF, QSizeF, QPointF

class ResizeHandle(QGraphicsRectItem):
    def __init__(self, position, parent=None):
        """
        position: string indicating handle position ('topleft', 'top', 'topright', etc.)
        """
        super().__init__(0, 0, 8, 8, parent)
        self.position = position
        self.setAcceptHoverEvents(True)
        self.setPen(QPen(QColor(0, 0, 0)))
        self.setBrush(QColor(255, 255, 255))
        self.setFlag(QGraphicsItem.ItemIsMovable, True)
        self.setFlag(QGraphicsItem.ItemSendsGeometryChanges, True)

        # Set cursor based on handle position
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

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            event.accept()
        else:
            super().mousePressEvent(event)

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

                if self.position in ['topleft', 'left', 'bottomleft']:
                    # Handle left-side resizing
                    delta_x = scene_pos.x() - text_box_pos.x()
                    new_width = text_rect.right() - delta_x
                    if new_width >= 50:  # Minimum width
                        new_rect.setLeft(delta_x)
                        text_box.setPos(text_box.pos().x() + delta_x, text_box.pos().y())
                        new_rect.setLeft(0)

                if self.position in ['topright', 'right', 'bottomright']:
                    # Handle right-side resizing
                    delta_x = scene_pos.x() - text_box_pos.x()
                    new_rect.setRight(delta_x)

                if self.position in ['topleft', 'top', 'topright']:
                    # Handle top-side resizing
                    delta_y = scene_pos.y() - text_box_pos.y()
                    new_height = text_rect.bottom() - delta_y
                    if new_height >= 30:  # Minimum height
                        new_rect.setTop(delta_y)
                        text_box.setPos(text_box.pos().x(), text_box.pos().y() + delta_y)
                        new_rect.setTop(0)

                if self.position in ['bottomleft', 'bottom', 'bottomright']:
                    # Handle bottom-side resizing
                    delta_y = scene_pos.y() - text_box_pos.y()
                    new_rect.setBottom(delta_y)

                # Apply minimum size constraints
                if new_rect.width() >= 50 and new_rect.height() >= 30:
                    text_box.resize(new_rect.width(), new_rect.height())

            event.accept()
        else:
            super().mouseMoveEvent(event)