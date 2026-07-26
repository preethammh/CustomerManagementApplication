import { useCallback, useState } from 'react';
import type { PaginationParams } from '../types/customer';

export const usePagination = (initialParams: PaginationParams) => {
  const [pagination, setPagination] = useState<PaginationParams>(initialParams);

  const updatePagination = useCallback((newParams: Partial<PaginationParams>) => {
    setPagination((prev) => {
      const nextPage = newParams.page !== undefined ? Math.max(0, newParams.page) : prev.page;

      return {
        ...prev,
        ...newParams,
        page: nextPage,
      };
    });
  }, []);

  const goToPage = useCallback((page: number) => {
    updatePagination({ page: Math.max(0, page) });
  }, [updatePagination]);

  const nextPage = useCallback(() => {
    setPagination((prev) => ({ ...prev, page: Math.max(0, prev.page + 1) }));
  }, []);

  const previousPage = useCallback(() => {
    setPagination((prev) => ({ ...prev, page: Math.max(0, prev.page - 1) }));
  }, []);

  const resetPagination = useCallback(() => {
    setPagination((prev) => ({ ...prev, page: 0 }));
  }, []);

  return {
    pagination,
    setPagination,
    updatePagination,
    goToPage,
    nextPage,
    previousPage,
    resetPagination,
  };
};
