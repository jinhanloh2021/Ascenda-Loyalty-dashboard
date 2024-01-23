import Link from "next/link";
import "@/styles/globals.css";

export const metadata = {
  title: "Admin Panel",
  description: "Admin Panel for some company",
  icons: {
    icon: "",
  },
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body className="bg-offWhite text-offBlack font-montserrat tracking-wide overflow-x-hidden flex flex-col min-h-screen">
        <nav className="bg-blue-800 py-4 px-16 text-slate-50 font-semibold flex flex-row justify-between items-center text-lg sticky top-0 z-50 col-span-12 row-span-2">
          <Link href="/" className="text-lg">
            Ascenda Admin Panel
          </Link>
        </nav>
        <main className="flex-grow px-10 py-10 flex items-center justify-center">
          {children}
        </main>
      </body>
    </html>
  );
}
