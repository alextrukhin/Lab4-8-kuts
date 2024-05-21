<template>
  <div class="wrapper">
    <div class="btn-container">
      <button>Checkout</button>
      <RouterLink :to="`/index`"><p>Exit to main menu</p></RouterLink>
    </div>
    <div class="left-container">
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
          <div class="price-colomn">
            <p>{{ product_price }}</p>
          </div>
          <div class="quantity-colomn">
            <p>{{ product_quantity }}</p>
          </div>
        </template>
      </div>
    </div>
    <div class="right-container">
      <form action="">
        <h2>Your details</h2>
        <div>
          <input
            v-model="formState.client_firstname"
            placeholder="First name"
            autocomplete="given-name"
            class="details input"
          />
          <div class="error" v-if="triedToSubmit && errors.client_firstname">
            {{ errors.client_firstname[0] }}
          </div>
        </div>
        <div>
          <input
            v-model="formState.client_lastname"
            placeholder="Last name"
            autocomplete="family-name"
            class="details input"
          />
          <div class="error" v-if="triedToSubmit && errors.client_lastname">
            {{ errors.client_lastname[0] }}
          </div>
        </div>
        <div>
          <input
            v-model="formState.client_phone"
            type="tel"
            placeholder="Phone"
            autocomplete="tel"
            class="details input"
          />
          <div class="error" v-if="triedToSubmit && errors.client_phone">
            {{ errors.client_phone[0] }}
          </div>
        </div>
        <div>
          <input
            v-model="formState.client_email"
            type="email"
            placeholder="Email"
            autocomplete="email"
            class="details input"
          />
          <div class="error" v-if="triedToSubmit && errors.client_email">
            {{ errors.client_email[0] }}
          </div>
        </div>
        <button type="submit" class="btn"><h2>Place order</h2></button>
      </form>
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
  flex-direction: row;
}
</style>
