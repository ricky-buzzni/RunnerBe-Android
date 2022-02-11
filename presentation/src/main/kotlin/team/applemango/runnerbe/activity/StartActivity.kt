/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 1. 31. 오후 4:49
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.activity

import android.os.Build
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import team.applemango.runnerbe.feature.home.board.BoardActivity
import team.applemango.runnerbe.shared.constant.DataStoreKey
import team.applemango.runnerbe.shared.util.extension.changeActivityWithAnimation
import team.applemango.runnerbe.shared.util.extension.collectWithLifecycle
import team.applemango.runnerbe.shared.util.extension.dataStore
import team.applemango.runnerbe.util.DFMLoginActivityAlias
import team.applemango.runnerbe.util.DFMOnboardActivityAlias

class StartActivity : AppCompatActivity() {

    /*private val editorConfig = ShapeBuilder()
        .withShapeSize(60f)
        .withShapeType(ShapeType.LINE)
        .withShapeColor(Color.LTGRAY)
        .withShapeOpacity(100)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.edit_activity)

        val editorView: PhotoEditorView = findViewById(R.id.editor)
        val editor = PhotoEditor.Builder(this, editorView)
            .setPinchTextScalable(false)
            .build()
            .also {
                it.setBrushDrawingMode(true)
                it.setShape(editorConfig)
            }

        val undo: Button = findViewById(R.id.undo)
        val redo: Button = findViewById(R.id.redo)
        val draw: Button = findViewById(R.id.draw)
        val eraser: Button = findViewById(R.id.eraser)

        undo.setOnClickListener {
            editor.undo()
        }

        redo.setOnClickListener {
            editor.redo()
        }

        eraser.setOnClickListener {
            editor.brushEraser()
        }

        draw.setOnClickListener {
            editor.setBrushDrawingMode(true)
        }*/

        // 무조건 다른 액티비티로 이동되므로 알아서 cancel 됨 (수동 cancel 불필요)
        applicationContext.dataStore.data.collectWithLifecycle(this) { preferences ->
            val isSignedUser = preferences[DataStoreKey.Login.Jwt] != null
            val isSnsLoginDone = preferences[DataStoreKey.Login.Uuid] != null
            when {
                isSignedUser -> { // JWT 존재
                    changeActivityWithAnimation<BoardActivity>() // XXX
                    // 얜 fragment 이여야 함
                    return@collectWithLifecycle
                }
                isSnsLoginDone -> { // SNS 로그인 완료 -> 온보딩 페이지로 이동
                    changeActivityWithAnimation<DFMOnboardActivityAlias>()
                    return@collectWithLifecycle
                }
                !isSnsLoginDone -> { // SNS 로그인 하기 전
                    changeActivityWithAnimation<DFMLoginActivityAlias>()
                    return@collectWithLifecycle
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.animate().run {
                    alpha(0f)
                    scaleX(0f)
                    scaleY(0f)
                    interpolator = AnticipateInterpolator()
                    duration = 200L
                    withEndAction { splashScreenView.remove() }
                    withLayer()
                    start()
                }
            }
        }
    }
}
