// Sistema simple de toast (puedes reemplazar con una librería como sonner o react-hot-toast)
export const toast = {
  success: (message: string) => {
    if (typeof window !== "undefined") {
      alert(`✅ ${message}`);
    }
  },
  error: (message: string) => {
    if (typeof window !== "undefined") {
      alert(`❌ ${message}`);
    }
  },
  info: (message: string) => {
    if (typeof window !== "undefined") {
      alert(`ℹ️ ${message}`);
    }
  },
};
