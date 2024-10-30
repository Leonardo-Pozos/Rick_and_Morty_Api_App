package com.example.rickmortyapp.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Trip_origin: ImageVector
    get() {
        if (_Trip_origin != null) {
            return _Trip_origin!!
        }
        _Trip_origin = ImageVector.Builder(
            name = "Trip_origin",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(480f, 880f)
                quadToRelative(-82f, 0f, -155f, -31.5f)
                reflectiveQuadToRelative(-127.5f, -86f)
                reflectiveQuadToRelative(-86f, -127.5f)
                reflectiveQuadTo(80f, 480f)
                quadToRelative(0f, -83f, 31.5f, -155.5f)
                reflectiveQuadToRelative(86f, -127f)
                reflectiveQuadToRelative(127.5f, -86f)
                reflectiveQuadTo(480f, 80f)
                quadToRelative(83f, 0f, 155.5f, 31.5f)
                reflectiveQuadToRelative(127f, 86f)
                reflectiveQuadToRelative(86f, 127f)
                reflectiveQuadTo(880f, 480f)
                quadToRelative(0f, 82f, -31.5f, 155f)
                reflectiveQuadToRelative(-86f, 127.5f)
                reflectiveQuadToRelative(-127f, 86f)
                reflectiveQuadTo(480f, 880f)
                moveToRelative(0f, -160f)
                quadToRelative(100f, 0f, 170f, -70f)
                reflectiveQuadToRelative(70f, -170f)
                reflectiveQuadToRelative(-70f, -170f)
                reflectiveQuadToRelative(-170f, -70f)
                reflectiveQuadToRelative(-170f, 70f)
                reflectiveQuadToRelative(-70f, 170f)
                reflectiveQuadToRelative(70f, 170f)
                reflectiveQuadToRelative(170f, 70f)
            }
        }.build()
        return _Trip_origin!!
    }

private var _Trip_origin: ImageVector? = null
