interface AuthenticateModalLiProps {
  label: String;
  description: String;
}

export default function AuthenticateModalLi({
  label,
  description,
}: AuthenticateModalLiProps) {
  return (
    <li className="flex flex-wrap">
      <label className="w-32">{label}</label>
      <span>{description}</span>
    </li>
  );
}
