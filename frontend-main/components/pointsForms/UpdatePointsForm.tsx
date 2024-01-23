'use client';
import { Button, buttonVariants } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { Form, FormControl, FormField, FormItem, FormLabel } from '../ui/form';
import { cn } from '@/lib/utils';
import { PointsBalance } from '@/interfaces/PointsBalance';
import { updatePointsByPointsId } from '@/api/fetchPointsBalance';

const pointsBalanceSchema = z.object({
  appName: z.preprocess((a) => String(z.string().parse(a)), z.string()),
  balance: z.preprocess(
    (a) => parseInt(z.string().parse(a), 10),
    z.number().gte(0, 'Points must be positive')
  ),
});

type PointsBalanceSchema = z.infer<typeof pointsBalanceSchema>;

type props = {
  pointsBalance: PointsBalance;
  jwt: string | undefined;
};

export default function UpdatePointsForm({ pointsBalance, jwt }: props) {
  const form = useForm<PointsBalanceSchema>({
    resolver: zodResolver(pointsBalanceSchema),
    defaultValues: {
      appName: pointsBalance.appName,
      balance: pointsBalance.balance,
    },
    mode: 'onTouched',
  });

  const onSubmit = (data: PointsBalanceSchema) => {
    console.log(data);
    const reqBody = {
      pointsId: pointsBalance.pointsId,
      balance: data.balance,
      appName: data.appName,
      userId: pointsBalance.userId,
    };
    updatePointsByPointsId(
      reqBody.pointsId,
      reqBody.balance,
      reqBody.appName,
      reqBody.userId,
      jwt
    );
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className='grid gap-4'>
        <FormField
          defaultValue={pointsBalance.appName}
          control={form.control}
          name='appName'
          render={({ field }) => (
            <FormItem className='grid grid-cols-4 items-center gap-4 focus:bg-red-300'>
              <FormLabel htmlFor='appName' className='text-right'>
                Application
              </FormLabel>
              <FormControl>
                <Input
                  id='appName'
                  className='col-span-3'
                  value={pointsBalance.appName}
                  name={field.name}
                  onBlur={field.onBlur}
                  onChange={field.onChange}
                  ref={field.ref}
                  disabled={true}
                />
              </FormControl>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name='balance'
          render={({ field }) => (
            <FormItem className='grid grid-cols-4 items-center gap-4'>
              <FormLabel htmlFor='balance' className='text-right'>
                Balance
              </FormLabel>
              <FormControl>
                <Input
                  id='balance'
                  type='number'
                  className='col-span-3'
                  {...field}
                />
              </FormControl>
            </FormItem>
          )}
        />
        <div className='flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2'>
          <Button
            disabled={!form.formState.dirtyFields.balance}
            type='submit'
            className={cn(
              buttonVariants({ variant: 'default' }),
              'bg-blue-800'
            )}
          >
            Update Balance
          </Button>
        </div>
      </form>
    </Form>
  );
}
