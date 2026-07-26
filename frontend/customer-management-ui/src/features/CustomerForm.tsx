import React, { useState } from 'react';
import type { CustomerFormInput } from '../types/customer';
import { Input } from '../components/Inputs';
import { Button } from '../components/Button';
import { customerApi } from '../api/customerApi';

interface CustomerFormProps {
  onCustomerCreated: () => void;
}

const initialFormState: CustomerFormInput = { firstName: '', lastName: '', dateOfBirth: '' };

export const CustomerForm: React.FC<CustomerFormProps> = ({ onCustomerCreated }) => {
  const [formData, setFormData] = useState<CustomerFormInput>(initialFormState);
  const [errors, setErrors] = useState<Partial<CustomerFormInput>>({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [apiError, setApiError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  const validate = (): boolean => {
    const newErrors: Partial<CustomerFormInput> = {};
    if (!formData.firstName.trim()) newErrors.firstName = 'First name is required';
    if (!formData.lastName.trim()) newErrors.lastName = 'Last name is required';
    if (!formData.dateOfBirth) {
      newErrors.dateOfBirth = 'Date of birth is required';
    } else if (new Date(formData.dateOfBirth) > new Date()) {
      newErrors.dateOfBirth = 'Date of birth cannot be in the future';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    if (errors[name as keyof CustomerFormInput]) {
      setErrors((prev) => ({ ...prev, [name]: undefined }));
    }
    if (successMessage) {
      setSuccessMessage(null);
    }
  };

  const handleSubmit = async (e: React.SubmitEvent) => {
    e.preventDefault();
    if (!validate()) return;

    setIsSubmitting(true);
    setApiError(null);
    setSuccessMessage(null);

    try {
      await customerApi.createCustomer(formData);
      setFormData(initialFormState);
      setSuccessMessage('Customer added successfully');
      onCustomerCreated();
    } catch (err) {
      setApiError(err instanceof Error ? err.message : 'An unexpected error occurred');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md border border-gray-200 max-w-md w-full flex flex-col gap-4">
      <h2 className="text-xl font-semibold text-gray-800 border-b pb-2">Add New Customer</h2>
      
      {apiError && <div className="p-3 bg-red-50 text-red-700 text-sm rounded border border-red-200">{apiError}</div>}
      {successMessage && (
        <div className="p-3 bg-green-50 text-green-700 text-sm rounded border border-green-200 flex items-center gap-2">
          <svg className="h-4 w-4 flex-shrink-0" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
            <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-7.25 7.25a1 1 0 01-1.414 0l-3.25-3.25a1 1 0 111.414-1.414l2.543 2.543 6.543-6.543a1 1 0 011.414 0z" clipRule="evenodd" />
          </svg>
          <span>{successMessage}</span>
        </div>
      )}

      <Input label="First Name" name="firstName" value={formData.firstName} onChange={handleChange} error={errors.firstName} placeholder="John" />
      <Input label="Last Name" name="lastName" value={formData.lastName} onChange={handleChange} error={errors.lastName} placeholder="Doe" />
      <Input label="Date of Birth" name="dateOfBirth" type="date" value={formData.dateOfBirth} onChange={handleChange} error={errors.dateOfBirth} />

      <Button type="submit" isLoading={isSubmitting} className="w-full mt-2">
        Save Customer
      </Button>
    </form>
  );
};
