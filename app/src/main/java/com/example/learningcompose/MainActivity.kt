package com.example.learningcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.learningcompose.ui.theme.LearningComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgressBarHolder()
        }
    }
}

@Composable
fun ProgressBarHolder()
{
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        CircularProgressBar(
            percentage = 0.8f,
            number = 100
        )
    }
}
@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color : Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration : Int = 1000,
    animDelay: Int = 0
)
{
    var animationPlayed  by remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius*2f)
    )
    {
        Canvas(modifier = Modifier.size(radius*2f))
        {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360* currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = Round)
            )
        }

        Text(
            text = (((currentPercentage.value)*number).toInt()).toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LearningAnimation()
{
    var sizeState by remember{ mutableStateOf(200.dp) }
    val size by animateDpAsState(
        targetValue = sizeState,
        spring(
            Spring.DampingRatioHighBouncy
        )
//        tween(
//            durationMillis = 3000,
//            delayMillis = 300,
//            easing = LinearOutSlowInEasing
//        )
    )

    Box(modifier = Modifier
        .size(size)
        .background(Color.Red),
        contentAlignment = Alignment.Center)
    {
        Button(onClick = {
            sizeState += 50.dp
        }){
            Text("Increase Size")
        }
    }
}


@Composable
fun LearningConstraintLayout()
{

    val constraints = ConstraintSet{
        val greenBox = createRefFor("greenbox")
        val redBox = createRefFor("redbox")

        // using the constraints
        constrain(greenBox)
        {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redBox)

        {
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }


    }
    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .background(Color.Green)
            .layoutId("greenbox")
        )
        Box(modifier = Modifier
            .background(Color.Red)
            .layoutId("redbox"))
    }
}
@Composable
fun ShowRecyclerView()
{
    LazyColumn {
        items(5000) {
            Text(
                text = "Item $it",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
        }
    }
}
