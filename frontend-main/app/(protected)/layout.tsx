import Navbar from '@/components/navbar';
import SideNav from '@/components/sidenav';
import '@/styles/globals.css';

export default function LoggedInLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang='en' suppressHydrationWarning>
      <body className='bg-offWhite text-offBlack font-montserrat tracking-wide overflow-x-hidden grid grid-rows-12 grid-cols-12 justify-start'>
        <Navbar />
        <SideNav />
        <main className='flex-grow px-10 py-10 overflow-y-auto col-span-10 row-span-10'>
          {children}
        </main>
      </body>
    </html>
  );
}
