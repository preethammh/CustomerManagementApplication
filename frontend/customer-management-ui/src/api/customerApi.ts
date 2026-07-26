import type { Customer, CustomerFormInput, PaginationParams, PaginatedResponse } from '../types/customer';

const BASE_URL = '/api/v1/customer-management';

export const customerApi = {
  async getCustomers({page,size,sortBy}:PaginationParams):Promise<PaginatedResponse<Customer>> {
    const queryParams = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
      sortBy,
    })

    const response = await fetch(`${BASE_URL}/customers?${queryParams}`);
    if(!response.ok) {
      throw new Error(`Failed to fetch customers: ${response.statusText}`);
    }

    return response.json();
  },

  async createCustomer(customerData:CustomerFormInput):Promise<Customer> {
    const response = await fetch(`${BASE_URL}/customer`, {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(customerData)
    });

    if(!response.ok) {
      throw new Error(`Failed to create customer: ${response.statusText}`);
    }

    return response.json();
  },
};