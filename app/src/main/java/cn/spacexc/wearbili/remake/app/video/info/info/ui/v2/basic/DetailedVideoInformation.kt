package cn.spacexc.wearbili.remake.app.video.info.info.ui.v2.basic

import BiliTextIcon
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cn.spacexc.wearbili.common.copyToClipboard
import cn.spacexc.wearbili.common.domain.time.secondToTime
import cn.spacexc.wearbili.common.domain.video.toShortChinese
import cn.spacexc.wearbili.remake.R
import cn.spacexc.wearbili.remake.app.video.info.info.ui.VideoInformationViewModel
import cn.spacexc.wearbili.remake.common.ToastUtils
import cn.spacexc.wearbili.remake.common.ui.IconText
import cn.spacexc.wearbili.remake.common.ui.OutlinedRoundButton
import cn.spacexc.wearbili.remake.common.ui.clickVfx
import cn.spacexc.wearbili.remake.common.ui.rememberMutableInteractionSource
import cn.spacexc.wearbili.remake.common.ui.theme.wearbiliFontFamily
import cn.spacexc.wearbili.remake.common.ui.titleBackgroundHorizontalPadding

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun DetailedVideoInformation(
    infoButtonSize: DpSize,
    animatedContentScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    navController: NavController,
    viewModel: VideoInformationViewModel,
    onGoBack: () -> Unit
) {
    val context = LocalContext.current
    viewModel.state.videoData?.view?.let { video ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
                Spacer(modifier = Modifier.height(2.dp))    //For fixing spacing glitches
                Text(
                    text = video.title,
                    style = TextStyle(
                        fontFamily = wearbiliFontFamily,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = with(sharedTransitionScope) {
                        Modifier
                            .offset(y = -4.dp)
                            .fillMaxWidth()
                            .padding(horizontal = titleBackgroundHorizontalPadding())
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "title"),
                                animatedVisibilityScope = animatedContentScope
                            )
                    }
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier.padding(horizontal = titleBackgroundHorizontalPadding())
                ) {
                    IconText(
                        text = "${video.stat.view.toShortChinese()}观看",
                        modifier = Modifier.alpha(0.7f),
                        fontSize = 11.sp
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_view_count),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }

                    IconText(
                        text = video.stat.vt.secondToTime(),
                        modifier = Modifier
                            .alpha(0.7f),
                        fontSize = 11.sp,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Timer,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }

                    IconText(
                        text = "${video.stat.danmaku.toShortChinese()}弹幕",
                        modifier = Modifier.alpha(0.7f),
                        fontSize = 11.sp
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_danmaku),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }

                    IconText(
                        text = video.bvid,
                        modifier = Modifier
                            .alpha(0.7f)
                            .clickVfx(onLongClick = {
                                video.bvid.copyToClipboard(context = context)
                                ToastUtils.showText(content = "已复制BV号")
                            }),
                        fontSize = 11.sp,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Movie,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.White
                        )
                    }
                }
                /*SmallUserCard(
                    avatar = video.owner.face,
                    username = video.owner.name,
                    useBiliImage = false,
                    textSizeScale = 1.1f,
                    mid = video.owner.mid,
                    context = context,
                    imageModifier = Modifier
                        .offset(y = 1.dp),
                    modifier = Modifier.padding(horizontal = titleBackgroundHorizontalPadding() - 2.dp, vertical = 4.dp)
                )*/
                Text(
                    text = video.desc,
                    fontFamily = wearbiliFontFamily,
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier.padding(
                        start = titleBackgroundHorizontalPadding(),
                        end = titleBackgroundHorizontalPadding(),
                        bottom = 6.dp
                    )
                )
            }
            OutlinedRoundButton(
                modifier = with(sharedTransitionScope) {
                    Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "infoButton"),
                            animatedContentScope
                        )
                        .padding(10.dp)
                        .size(infoButtonSize)
                        .align(Alignment.BottomEnd)
                },
                buttonModifier = Modifier.aspectRatio(1f),
                interactionSource = rememberMutableInteractionSource(), icon = {
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BiliTextIcon(icon = "EAC0", size = 18.sp)
                    }
                },
                text = "",
                onClick = {
                    onGoBack()
                }
            )
        }
    }
}