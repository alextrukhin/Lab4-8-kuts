<template>
  <div class="wrapper">
    <div class="btn-container">
      <button>Checkout</button>
      <RouterLink :to="`/index`"><p>Exit to main menu</p></RouterLink>
    </div>
    <div class="price-container">
      <p>Total price - $</p>
    </div>
    <div class="table-container">
      <template
        #item="{
          id,
          product_name,
          product_color,
          product_manufacturer,
          product_price,
          product_image,
          product_quantity
        }"
      >
        <div class="img-colomn">
          <img src="product_image" alt="" />
        </div>
        <div class="name-colomn">
          <p>{{ product_name }}</p>
        </div>
        <div class="color-colomn">
          <p>{{ product_color }}</p>
        </div>
        <div class="manufacturer-colomn">
          <p>{{ product_manufacturer }}</p>
        </div>
        <div class="price-colomn">
          <p>{{ product_price }}</p>
        </div>
        <div class="quantity-colomn">
          <button>-</button>
          <p>{{ product_quantity }}</p>
          <button>+</button>
        </div>
        <div class="btn-colomn"><button>Delete from cart</button></div>
      </template>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
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
