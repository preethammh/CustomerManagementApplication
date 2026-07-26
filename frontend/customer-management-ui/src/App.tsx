import React from 'react';
import { CustomerForm } from './features/CustomerForm';
import { CustomerList } from './features/CustomerList';
import { useFetchCustomers } from './hooks/useFetchCustomers';

const App: React.FC = () => {
  const { data, loading, error, pagination, updatePagination, refresh } = useFetchCustomers({
    page: 0,
    size: 5,
    sortBy: 'firstName',
  });

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <header className="bg-white border-b border-gray-200 py-4 px-6 shadow-sm">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold text-gray-900 tracking-tight">CRM Portal</h1>
          <span className="bg-blue-50 text-blue-700 text-xs px-2.5 py-1 rounded-full font-semibold uppercase tracking-wider">
            Management Application
          </span>
        </div>
      </header>

      <main className="max-w-7xl w-full mx-auto p-4 md:p-8 flex flex-col lg:flex-row gap-8 flex-1 items-start">
        {/* Creation Feature Panel */}
        <CustomerForm onCustomerCreated={refresh} />

        {/* Viewing Feature Panel */}
        <CustomerList
          data={data}
          loading={loading}
          error={error}
          pagination={pagination}
          onPaginationChange={updatePagination}
        />
      </main>
    </div>
  );
};

export default App;
