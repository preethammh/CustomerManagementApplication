export interface Customer {
  id?: number;
  firstName: string;
  lastName: string;
  dateOfBirth: string; // ISO format (YYYY-MM-DD)
}

export interface CustomerFormInput {
  firstName: string;
  lastName: string;
  dateOfBirth: string;
}

export interface PaginationParams {
  page: number;
  size: number;
  sortBy: string;
}

export interface PaginatedResponse<T> {
  customers: T[];
  metadata: {
    currentPage: number;
    perPage: number;
    totalPages: number;
    totalItems: number;
  };
}
