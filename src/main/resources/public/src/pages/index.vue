<template>
  <div class="wrapper">
    <div class="btn-container">
      <button>View the products in the cart</button>
      <RouterLink :to="`/orders`"><p>View my orders</p></RouterLink>
    </div>
    <div class="table-container">
      <div v-for="product in products" :key="product.id">
        <div class="img-colomn">
          <img :src="product.image" alt="" />
        </div>
        <div class="name-colomn">
          <p>{{ product.name }}</p>
        </div>
        <div class="color-colomn">
          <p>{{ product.color }}</p>
        </div>
        <div class="manufacturer-colomn">
          <p>{{ product.manufacturer }}</p>
        </div>
        <div class="price-colomn">
          <p>${{ product.price }}</p>
        </div>
        <div class="btn-colomn">
          <div v-if="amountInCart(product.id)">
            <button @click="setInCart(product.id, amountInCart(product.id) - 1)">-</button>
            <input
              :value="amountInCart(product.id)"
              type="number"
              @input="setInCart(product.id, $event.target.value)"
            />
            <button @click="setInCart(product.id, amountInCart(product.id) + 1)">+</button>
          </div>
          <button v-if="!amountInCart(product.id)" @click="setInCart(product.id, 1)">
            Add to cart
          </button>
          <button v-else @click="setInCart(product.id, 0)">Remove from cart</button>
        </div>
      </div>
    </div>
    <form v-show="order.products?.length" @submit.prevent="submitOrder">
      <input v-model="order.name" placeholder="Name" type="text" />
      <input v-model="order.email" placeholder="Email" type="email" />
      <input v-model="order.phone" placeholder="Phone" type="tel" />
      <button type="submit">Submit order</button>
    </form>
  </div>
</template>
<script setup lang="ts">
import { Order, Product } from '@/types'
import { ref } from 'vue'

const products = ref<Product[]>([])
const order = ref<Order>({
  id: -1,
  products: [],
  totalPrice: 0,
  trackingNumber: '',
  status: 'NEW',
  name: '',
  email: '',
  phone: ''
})

const refreshProducts = () => {
  fetch('http://localhost:8080/getProducts')
    .then((res) => res.json())
    .then((data) => (products.value = data))
}
refreshProducts()

const submitOrder = async () => {
  await fetch('http://localhost:8080/createOrder', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      ...order.value,
      totalPrice: order.value.products.reduce((acc, p) => {
        const product = products.value.find((pr) => pr.id === p.productId)
        return acc + (product?.price || 0) * p.quantity
      }, 0)
    })
  })
  order.value = {
    id: -1,
    products: [],
    totalPrice: 0,
    trackingNumber: '',
    status: 'NEW',
    name: '',
    email: '',
    phone: ''
  }
  alert('Order submitted')
}

const amountInCart = (productId: number) => {
  return order.value.products.find((p) => p.productId === productId)?.quantity || 0
}
const setInCart = (productId: number, quantity: number) => {
  const product = order.value.products.find((p) => p.productId === productId)
  if (product) {
    product.quantity = quantity
  } else {
    order.value.products.push({ productId, quantity })
  }
  order.value.totalPrice = order.value.products.reduce((acc, p) => {
    const product = products.value.find((pr) => pr.id === p.productId)
    return acc + (product?.price || 0) * p.quantity
  }, 0)
}
</script>
<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  flex-direction: colomn;
}
</style>
