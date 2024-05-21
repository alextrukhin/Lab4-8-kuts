export type Product = {
  id: number
  name: string
  color: 'RED' | 'GREEN' | 'BLUE' | 'ORANGE' | 'YELLOW' | 'BLACK' | 'WHITE'
  price: number
  manufacturer: string
  description: string
  image: string
  quantity: number
}

export type OrderProduct = {
  productId: number
  quantity: number
}

export type Order = {
  id: number
  products: OrderProduct[]
  totalPrice: number
  trackingNumber: string
  status: 'NEW' | 'DELIVERING' | 'COMPLETED' | 'CANCELED'
  name: string
  email: string
  phone: string
}
