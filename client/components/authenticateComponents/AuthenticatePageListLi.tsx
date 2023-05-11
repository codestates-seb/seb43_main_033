interface AuthenticatePageListLiProps {
  label: string;
  description: string;
}

export default function AuthenticatePageListLi({
  label,
  description,
}: AuthenticatePageListLiProps) {
  return (
    <div className="flex">
      <label className="w-40">{label}</label>
      <span>{description}</span>
    </div>
  );
}
