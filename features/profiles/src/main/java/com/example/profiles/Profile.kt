package com.example.profiles

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.model.Order
import com.example.model.OrderWithProductQuantity
import com.example.model.Product
import com.example.model.ProductQuantity
import com.example.model.ProductQuantityAndProduct


@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun Orders(
    orders: List<OrderWithProductQuantity>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {

    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            ),
            exit = fadeOut(),
            modifier = modifier
        ) {
            LazyColumn(modifier = modifier) {
                orders.forEach { order ->
                    item {
                        Text(
                            text = "Order №${order.order.orderId}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )

                        val items = order.productQuantity.sumOf {
                            it.productQuantity.quantity
                        }
                        val total = order.productQuantity.sumOf {
                            it.product.productPrice * it.productQuantity.quantity
                        }
                        Text(
                            text = "$items product for $total р.",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                        Text(
                            text = "Recieved deliverycoins",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                    }
                    itemsIndexed(order.productQuantity) { index, productQuantity ->
                        ProductListItem(
                            productQuantity = productQuantity,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                // Animate each list item to slide in vertically
                                .animateEnterExit(
                                    enter = slideInVertically(
                                        animationSpec = spring(
                                            stiffness = Spring.StiffnessVeryLow,
                                            dampingRatio = Spring.DampingRatioLowBouncy
                                        ),
                                        initialOffsetY = { it * (index + 1) } // staggered entrance
                                    )
                                )
                        )
                    }

                }
            }

        }
}

@Composable
fun ProductListItem(productQuantity: ProductQuantityAndProduct, modifier: Modifier = Modifier) {
    // Create a column so that texts don't overlap
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.padding(vertical = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))

                ) {
                    AsyncImage(
                        model = productQuantity.product.image,
                        contentDescription = productQuantity.product.image,
                        placeholder = painterResource(id = com.example.data.R.drawable.ic_menu_24),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                    )
                }
                Column {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = productQuantity.product.nameProduct,
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        text = productQuantity.product.productCategory,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(end = 8.dp)

                    )
                }
            }
            Divider(Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "${productQuantity.product.productPrice} р.",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Item ordered: ${productQuantity.productQuantity.quantity} ",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductPreview(modifier: Modifier = Modifier) {
    // Create a box to overlap image and texts
    Box(modifier) {
        ProductListItem(
            productQuantity = ProductQuantityAndProduct(
                ProductQuantity(id = 1, orderId = 1, quantity = 4),
                Product(
                    id = 1,
                    nameProduct = "Food",
                    productCategory = "Chicken",
                    200,
                    3,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTx1z3Nmzsk_fDBn84ZFlNJYigwwuyg419aYnDLwbG4CQ&s"
                )
            ), modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview()
@Composable
private fun OrderCardPreview() {
    HappyBirthdayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Orders(
                listOf(
                    OrderWithProductQuantity(
                        Order(
                            orderId = 1,
                            totalPrice = 444,
                        ),
                        listOf(
                            ProductQuantityAndProduct(
                                ProductQuantity(id = 1, orderId = 1, quantity = 4),
                                Product(
                                    id = 1,
                                    nameProduct = "Food",
                                    productCategory = "Chicken",
                                    200,
                                    3,
                                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTx1z3Nmzsk_fDBn84ZFlNJYigwwuyg419aYnDLwbG4CQ&s"
                                )
                            )
                        )
                    )
                )
            )
        }
    }
}