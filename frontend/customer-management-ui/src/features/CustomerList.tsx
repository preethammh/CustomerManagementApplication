import React from 'react';
import type { Customer, PaginatedResponse, PaginationParams } from '../types/customer';
import { Button } from '../components/Button';

interface CustomerListProps {
  data: PaginatedResponse<Customer> | null;
  loading: boolean;
  error: string | null;
  pagination: PaginationParams;
  onPaginationChange: (newParams: Partial<PaginationParams>) => void;
}

export const CustomerList: React.FC<CustomerListProps> = ({
  data,
  loading,
  error,
  pagination,
  onPaginationChange,
}) => {
  const handleSortChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    onPaginationChange({ sortBy: e.target.value, page: 0 });
  };

  if (error) return <div className="p-4 bg-red-50 text-red-700 border border-red-200 rounded-md">Error loading list: {error}</div>;

  const hasPreviousPage = data ? data.metadata.currentPage > 0 : false;
  const hasNextPage = data ? data.metadata.currentPage < data.metadata.totalPages - 1 : false;

  return (
    <div className="bg-white rounded-lg shadow-md border border-gray-200 flex-1 p-6 flex flex-col gap-4">
      <div className="flex justify-between items-center flex-wrap gap-2">
        <h2 className="text-xl font-semibold text-gray-800">Customer Records</h2>
        <div className="flex items-center gap-2">
          <label className="text-sm text-gray-600 font-medium">Sort By:</label>
          <select
            value={pagination.sortBy}
            onChange={handleSortChange}
            className="border border-gray-300 rounded-md p-1.5 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="firstName">First Name</option>
            <option value="lastName">Last Name</option>
            <option value="dateOfBirth">Date of Birth</option>
          </select>
        </div>
      </div>

      <div className="overflow-x-auto border rounded-md">
        <table className="min-w-full divide-y divide-gray-200 text-left text-sm">
          <thead className="bg-gray-50 text-gray-700 font-medium uppercase tracking-wider text-xs">
            <tr>
              <th className="px-6 py-3">First Name</th>
              <th className="px-6 py-3">Last Name</th>
              <th className="px-6 py-3">Date of Birth</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200 text-gray-900 bg-white">
            {loading && !data ? (
              <tr>
                <td colSpan={3} className="text-center py-8 text-gray-500">Loading customer details...</td>
              </tr>
            ) : data?.customers.length === 0 ? (
              <tr>
                <td colSpan={3} className="text-center py-8 text-gray-500">No customer records found.</td>
              </tr>
            ) : (
              data?.customers.map((customer) => (
                <tr key={customer.id} className="hover:bg-gray-50 transition">
                  <td className="px-6 py-4 whitespace-nowrap">{customer.firstName}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{customer.lastName}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    {new Date(customer.dateOfBirth).toLocaleDateString(undefined, {
                      year: 'numeric',
                      month: 'long',
                      day: 'numeric',
                    })}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {/* Pagination Footer Controls updated for metadata schema */}
      {data && data.metadata.totalPages > 0 && (
        <div className="flex justify-between items-center border-t pt-4 mt-auto">
          <span className="text-xs text-gray-600">
            Showing Page <strong>{data.metadata.currentPage + 1}</strong> of <strong>{data.metadata.totalPages}</strong> ({data.metadata.totalItems} entries)
          </span>
          <div className="flex gap-2">
            <Button
              variant="secondary"
              disabled={!hasPreviousPage || loading}
              onClick={() => onPaginationChange({ page: pagination.page - 1 })}
            >
              Previous
            </Button>
            <Button
              variant="secondary"
              disabled={!hasNextPage || loading}
              onClick={() => onPaginationChange({ page: pagination.page + 1 })}
            >
              Next
            </Button>
          </div>
        </div>
      )}
    </div>
  );
};
