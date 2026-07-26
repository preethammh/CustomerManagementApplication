import { useState, useEffect, useCallback } from 'react';
import type { Customer, PaginatedResponse, PaginationParams } from '../types/customer';
import { customerApi } from '../api/customerApi';
import { usePagination } from './usePagination';

export const useFetchCustomers = (initialParams: PaginationParams) => {
  const { pagination, updatePagination, resetPagination } = usePagination(initialParams);
  const [data, setData] = useState<PaginatedResponse<Customer> | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchRecords = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await customerApi.getCustomers(pagination);
      setData(response);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred while loading customers');
    } finally {
      setLoading(false);
    }
  }, [pagination]);

  useEffect(() => {
    fetchRecords();
  }, [fetchRecords]);

  return {
    data,
    loading,
    error,
    pagination,
    updatePagination,
    refresh: fetchRecords,
    resetPagination,
  };
};
