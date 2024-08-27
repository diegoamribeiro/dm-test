package br.com.deliverymuch.coupons

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.deliverymuch.coupons.ui.theme.CouponsTheme

class MainActivity : ComponentActivity() {
    private lateinit var couponRepository: CouponRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        couponRepository = CouponRepository(this)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.LightGray.toArgb(),
                Color.DarkGray.toArgb()
            )
        )
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                CouponList(
                    paddingValues = paddingValues,
                    couponRepository.lodCupons() ?: emptyList()
                )
            }
        }
    }
}

@Composable
fun CouponList(
    paddingValues: PaddingValues,
    loadedCoupons: List<Coupon>
) {
    val coupons = remember {
        mutableStateOf(
            loadedCoupons
        )
    }

    Column(modifier = Modifier.padding(paddingValues)) {
        for ((index, coupon) in coupons.value.withIndex()) {
            CouponItem(coupon) { isUsed ->
                val updatedCoupons = coupons.value.toMutableList()
                updatedCoupons[index] = coupon.copy(isUsed = isUsed)
                coupons.value = updatedCoupons


                Log.d("CouponItem", "${coupons.value[index + 83]}º was clicked")
            }
        }
    }
}

@Composable
fun CouponItem(coupon: Coupon, onUsedChange: (Boolean) -> Unit) {
    Log.d("CouponItem", "Recomposing for coupon: ${coupon.code}")

    val isUsed = remember {
        mutableStateOf(coupon.isUsed)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
    ) {
        CouponTitle(coupon)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Código: ${coupon.code}", style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.tertiary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isUsed.value,
                onCheckedChange = { checked ->
                    isUsed.value = checked
                    onUsedChange(checked)
                }
            )
            Text(text = "Já usei!")
        }
    }
}

@Composable
private fun CouponTitle(coupon: Coupon) {
    Log.d("CouponItem", "Recomposing title for coupon: ${coupon.code}")

    Text(text = coupon.description, fontSize = 18.sp, fontWeight = FontWeight.Medium)
}


data class Coupon(val description: String, val code: String, val isUsed: Boolean = false)

@Composable
@Preview
fun PreviewCouponList() {
    Surface {
        CouponsTheme {
            CouponList(
                paddingValues = PaddingValues(8.dp),
                loadedCoupons = listOf(
                    Coupon("10% off no seu próximo pedido!", "TOMA10", false),
                    Coupon("Entrega grátis em pedidos acima de R$30", "MOTOBOY", true),
                    Coupon("R$5 off no seu primeiro pedido!", "BEMVINDO5", false),
                )
            )
        }
    }
}